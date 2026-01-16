package com.williamcallahan.tui4j.examples.progress.animated;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress.FrameMsg;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress.Progress;

import java.util.Random;

public class ProgressAnimatedExample implements Model {

    private final Progress progress;
    private final Random random;
    private double targetPercent;
    private long lastUpdateTime;
    private boolean quitting;
    private boolean autoMode;

    public ProgressAnimatedExample() {
        this.progress = new Progress()
                .withWidth(50)
                .withShowPercentage(true)
                .withDefaultGradient();
        this.random = new Random();
        this.targetPercent = 0.0;
        this.lastUpdateTime = System.currentTimeMillis();
        this.quitting = false;
        this.autoMode = true;
    }

    @Override
    public Command init() {
        return progress.setPercent(0.0);
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            if ("q".equals(key) || "Q".equals(key) || "ctrl+c".equals(key)) {
                quitting = true;
                return new UpdateResult<>(this, QuitMessage::new);
            }
            if (" ".equals(key)) {
                autoMode = !autoMode;
            } else if ("r".equals(key)) {
                targetPercent = 0.0;
                return new UpdateResult<>(this, progress.setPercent(0.0));
            } else if ("1".equals(key)) {
                targetPercent = 0.1;
            } else if ("2".equals(key)) {
                targetPercent = 0.25;
            } else if ("3".equals(key)) {
                targetPercent = 0.5;
            } else if ("4".equals(key)) {
                targetPercent = 0.75;
            } else if ("5".equals(key)) {
                targetPercent = 1.0;
            } else if ("+".equals(key) || "=".equals(key)) {
                targetPercent = Math.min(1.0, targetPercent + 0.1);
            } else if ("-".equals(key) || "_".equals(key)) {
                targetPercent = Math.max(0.0, targetPercent - 0.1);
            }
            return new UpdateResult<>(this, progress.setPercent(targetPercent));
        }

        if (msg instanceof FrameMsg frameMsg) {
            Command cmd = null;
            if (autoMode && System.currentTimeMillis() - lastUpdateTime > 1500) {
                if (progress.percentShown() >= targetPercent - 0.001) {
                    targetPercent = random.nextDouble();
                }
                lastUpdateTime = System.currentTimeMillis();
                cmd = progress.setPercent(targetPercent);
            }

            UpdateResult<? extends Model> updateResult = progress.update(msg);
            Command combinedCmd = Command.batch(updateResult.command(), cmd);
            return UpdateResult.from(this, combinedCmd);
        }

        if (msg instanceof com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress.SetPercentMsg setPercentMsg) {
            targetPercent = setPercentMsg.percent();
            UpdateResult<? extends Model> updateResult = progress.update(msg);
            return UpdateResult.from(this, updateResult.command());
        }

        UpdateResult<? extends Model> updateResult = progress.update(msg);
        return UpdateResult.from(this, updateResult.command());
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  Animated Progress Bar with Spring Animation\n");
        sb.append("  ===========================================\n\n");

        sb.append("  Progress: ").append(progress.view()).append("\n\n");

        sb.append("  Animating: ").append(progress.isAnimating() ? "Yes" : "No").append("\n");
        sb.append("  Target:    ").append(String.format("%.0f%%", targetPercent * 100)).append("\n");
        sb.append("  Shown:     ").append(String.format("%.0f%%", progress.percentShown() * 100)).append("\n\n");

        sb.append("  Controls:\n");
        sb.append("    SPACE    Toggle auto mode: ").append(autoMode ? "ON" : "OFF").append("\n");
        sb.append("    1-5      Set target to 10%, 25%, 50%, 75%, 100%\n");
        sb.append("    +/-      Adjust target by 10%\n");
        sb.append("    r        Reset to 0%\n");
        sb.append("    q        Quit\n\n");

        return sb.toString();
    }

    public static void main(String[] args) {
        new Program(new ProgressAnimatedExample()).run();
    }
}
