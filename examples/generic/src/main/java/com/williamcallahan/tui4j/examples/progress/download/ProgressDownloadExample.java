package com.williamcallahan.tui4j.examples.progress.download;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress.FrameMsg;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress.Progress;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProgressDownloadExample implements Model {

    private static final Duration TICK_INTERVAL = Duration.ofMillis(50);

    private final Progress progressBar;
    private final List<DownloadFile> downloads;
    private int selectedIndex;
    private boolean quitting;
    private boolean allComplete;

    public ProgressDownloadExample() {
        this.progressBar = new Progress()
                .withWidth(50)
                .withShowPercentage(true)
                .withPercentFormat(" %5.1f%%")
                .withFullColor("#28A745");
        this.downloads = new ArrayList<>();
        this.selectedIndex = 0;
        this.quitting = false;
        this.allComplete = false;

        downloads.add(new DownloadFile("ubuntu-22.04.iso", 3800, 450));
        downloads.add(new DownloadFile("node-v18.16.0.pkg", 180, 80));
        downloads.add(new DownloadFile("dataset-v2.csv", 950, 120));
    }

    @Override
    public Command init() {
        return Command.tick(TICK_INTERVAL, time -> new DownloadTickMessage());
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            if ("q".equals(key) || "Q".equals(key) || "ctrl+c".equals(key)) {
                quitting = true;
                return new UpdateResult<>(this, QuitMessage::new);
            }
            if ("j".equals(key) || "down".equals(key)) {
                selectedIndex = Math.min(selectedIndex + 1, downloads.size() - 1);
            } else if ("k".equals(key) || "up".equals(key)) {
                selectedIndex = Math.max(selectedIndex - 1, 0);
            } else if (" ".equals(key)) {
                downloads.get(selectedIndex).togglePause();
            } else if ("r".equals(key)) {
                for (DownloadFile download : downloads) {
                    download.reset();
                }
                selectedIndex = 0;
            }
        }

        if (msg instanceof DownloadTickMessage) {
            boolean anyDownloading = false;
            for (DownloadFile download : downloads) {
                download.update();
                if (download.isDownloading()) {
                    anyDownloading = true;
                }
            }

            allComplete = downloads.stream().allMatch(DownloadFile::isComplete);

            if (!allComplete) {
                Command cmd = Command.tick(TICK_INTERVAL, time -> new DownloadTickMessage());
                return UpdateResult.from(this, cmd);
            }
        }

        if (msg instanceof FrameMsg frameMsg) {
            UpdateResult<? extends Model> updateResult = progressBar.update(msg);
            return UpdateResult.from(this, updateResult.command());
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n  File Download Manager\n");
        sb.append("  =====================\n\n");

        for (int i = 0; i < downloads.size(); i++) {
            DownloadFile download = downloads.get(i);
            String prefix = i == selectedIndex ? ">" : " ";
            String icon = download.isComplete() ? "✓" : (download.isPaused() ? "⏸" : "↓");
            String status = download.isComplete() ? "Done" : (download.isPaused() ? "Paused" : download.getSpeedFormatted());
            String eta = download.isComplete() ? "" : (download.isPaused() ? "Paused" : "ETA: " + download.getEtaFormatted());

            if (i == selectedIndex) {
                sb.append(prefix).append(" ").append(icon).append(" ").append(download.getName()).append("\n");
                progressBar.setPercent(download.getPercent());
                sb.append("      ").append(progressBar.view()).append("\n");
                sb.append("      ").append(download.getSizeFormatted()).append(" / ").append(download.getTotalSizeFormatted())
                        .append("  ").append(status).append("\n");
                if (!download.isComplete() && !download.isPaused()) {
                    sb.append("      ").append(eta).append("\n");
                }
            } else {
                double displayPercent = download.getPercent();
                Progress tempProgress = new Progress()
                        .withWidth(30)
                        .withShowPercentage(false)
                        .withFullColor("#28A745");
                sb.append(prefix).append(" ").append(icon).append(" ").append(download.getName());
                sb.append("  ").append(tempProgress.viewAs(displayPercent));
                sb.append(String.format(" %3.0f%%", displayPercent * 100));
                sb.append("\n");
            }
        }

        sb.append("\n  Controls: j/k or up/down to select, space to pause/resume, r to reset, q to quit\n");

        if (allComplete) {
            sb.append("\n  All downloads complete!\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        new Program(new ProgressDownloadExample()).run();
    }

    private static class DownloadTickMessage implements Message {
    }

    private static class DownloadFile {
        private final String name;
        private final long totalBytes;
        private long downloadedBytes;
        private double lastSpeed;
        private boolean paused;
        private LocalDateTime lastUpdate;
        private long startTime;
        private long bytesAtSpeedUpdate;
        private LocalDateTime speedUpdateTime;

        public DownloadFile(String name, long totalBytes, long downloadSpeed) {
            this.name = name;
            this.totalBytes = totalBytes * 1024 * 1024;
            this.downloadedBytes = 0;
            this.lastSpeed = downloadSpeed * 1024;
            this.paused = false;
            this.lastUpdate = LocalDateTime.now();
            this.startTime = System.currentTimeMillis();
            this.bytesAtSpeedUpdate = 0;
            this.speedUpdateTime = LocalDateTime.now();
        }

        public void update() {
            if (paused || isComplete()) return;

            long bytesToDownload = (long) (lastSpeed * 0.05);
            downloadedBytes = Math.min(downloadedBytes + bytesToDownload, totalBytes);
            lastUpdate = LocalDateTime.now();

            updateSpeedEstimate();
        }

        private void updateSpeedEstimate() {
            long now = System.currentTimeMillis();
            long elapsed = now - (speedUpdateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli());
            if (elapsed > 500) {
                long bytesDownloaded = downloadedBytes - bytesAtSpeedUpdate;
                if (bytesDownloaded > 0) {
                    double newSpeed = (double) bytesDownloaded / (elapsed / 1000.0);
                    lastSpeed = lastSpeed * 0.7 + newSpeed * 0.3;
                }
                bytesAtSpeedUpdate = downloadedBytes;
                speedUpdateTime = LocalDateTime.now();
            }
        }

        public void togglePause() {
            if (!isComplete()) {
                paused = !paused;
            }
        }

        public void reset() {
            downloadedBytes = 0;
            paused = false;
        }

        public double getPercent() {
            return (double) downloadedBytes / totalBytes;
        }

        public boolean isDownloading() {
            return !paused && !isComplete();
        }

        public boolean isComplete() {
            return downloadedBytes >= totalBytes;
        }

        public boolean isPaused() {
            return paused;
        }

        public String getName() {
            return name;
        }

        public String getSizeFormatted() {
            return formatBytes(downloadedBytes);
        }

        public String getTotalSizeFormatted() {
            return formatBytes(totalBytes);
        }

        public String getSpeedFormatted() {
            return formatBytes((long) lastSpeed) + "/s";
        }

        public String getEtaFormatted() {
            if (isComplete()) return "";
            long remainingBytes = totalBytes - downloadedBytes;
            if (remainingBytes <= 0) return "Almost done";
            double seconds = remainingBytes / lastSpeed;
            if (seconds < 1) return "< 1s";
            if (seconds < 60) return String.format("%ds", (int) seconds);
            int minutes = (int) (seconds / 60);
            if (minutes < 60) return String.format("%dm %ds", minutes, (int) (seconds % 60));
            int hours = minutes / 60;
            return String.format("%dh %dm", hours, minutes % 60);
        }

        private String formatBytes(long bytes) {
            if (bytes >= 1024 * 1024 * 1024) {
                return String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024));
            } else if (bytes >= 1024 * 1024) {
                return String.format("%.1f MB", bytes / (1024.0 * 1024));
            } else if (bytes >= 1024) {
                return String.format("%.1f KB", bytes / 1024.0);
            }
            return bytes + " B";
        }
    }
}
