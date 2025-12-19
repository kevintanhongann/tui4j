package org.flatscrew.latte;

import org.flatscrew.latte.ansi.Code;
import org.flatscrew.latte.ansi.Truncate;
import org.flatscrew.latte.message.CopyToClipboardMessage;
import org.flatscrew.latte.message.DisableMouseMessage;
import org.flatscrew.latte.message.EnableMouseAllMotionMessage;
import org.flatscrew.latte.message.EnableMouseCellMotionMessage;
import org.flatscrew.latte.message.PrintLineMessage;
import org.flatscrew.latte.message.ResetMouseCursorMessage;
import org.flatscrew.latte.message.SetMouseCursorPointerMessage;
import org.flatscrew.latte.message.SetMouseCursorTextMessage;
import org.flatscrew.latte.message.SetWindowTitleMessage;
import org.flatscrew.latte.message.WindowSizeMessage;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StandardRenderer implements Renderer {

    private static final int DEFAULT_FPS = 60;

    private volatile boolean needsRender = true;
    private final Lock renderLock = new ReentrantLock();
    private final Terminal terminal;
    private volatile boolean isRunning = false;
    private final StringBuilder buffer = new StringBuilder();
    private volatile String lastRender = "";
    private final ScheduledExecutorService ticker;
    private final long frameTime;
    private String[] lastRenderedLines = new String[0];
    private final List<String> queuedMessageLines = new ArrayList<>();

    private int linesRendered = 0;
    private int width;
    private int height;
    private boolean isInAltScreen;
    private boolean isReportFocus;

    public StandardRenderer(Terminal terminal) {
        this(terminal, DEFAULT_FPS);
    }

    public StandardRenderer(Terminal terminal, int fps) {
        this.terminal = terminal;
        this.frameTime = 1000 / Math.min(Math.max(fps, 1), 120);
        this.ticker = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "Latte-Renderer-Thread");
            t.setDaemon(true);
            return t;
        });

        try {
            this.width = terminal.getWidth();
            this.height = terminal.getHeight();
        } catch (Throwable t) {
            this.width = 80;
            this.height = 24;
        }
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            ticker.scheduleAtFixedRate(this::flush, 0, frameTime, TimeUnit.MILLISECONDS);
        }
    }

    public void stop() {
        isRunning = false;
        try {
            ticker.shutdownNow();
            ticker.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new ProgramException(e);
        }
    }

    private void flush() {
        if (!needsRender) {
            return;
        }

        renderLock.lock();
        try {
            if (buffer.isEmpty() || buffer.toString().equals(lastRender)) {
                return;
            }

            StringBuilder outputBuffer = new StringBuilder();
            String[] newLines = buffer.toString().split("\n");

            if (height > 0 && newLines.length > height) {
                newLines = Arrays.copyOfRange(newLines, newLines.length - height, newLines.length);
            }

            if (linesRendered > 1) {
                outputBuffer.append("\033[").append(linesRendered - 1).append("A");
            }

            boolean flushQueuedMessages = !queuedMessageLines.isEmpty() && !isInAltScreen;
            if (flushQueuedMessages) {
                for (String line : queuedMessageLines) {
                    if (width > 0 && line.length() < width) {
                        outputBuffer.append(line).append("\033[K");
                    } else {
                        outputBuffer.append(line);
                    }
                    outputBuffer.append("\r\n");
                }
                queuedMessageLines.clear();
            }

            for (int i = 0; i < newLines.length; i++) {
                boolean canSkip = !flushQueuedMessages &&
                        lastRenderedLines.length > i &&
                        newLines[i].equals(lastRenderedLines[i]);

                if (canSkip) {
                    if (i < newLines.length - 1) {
                        outputBuffer.append("\033[B");
                    }
                    continue;
                }

                String line = newLines[i];

                if (this.width > 0) {
                    line = Truncate.truncate(line, this.width, "");
                }

                if (width > 0 && line.length() < width) {
                    outputBuffer.append("\r").append(line).append("\033[K");
                } else {
                    outputBuffer.append("\r").append(line);
                }

                if (i < newLines.length - 1) {
                    outputBuffer.append("\n");
                }
            }

            if (linesRendered > newLines.length) {
                outputBuffer.append("\033[J");
            }

            outputBuffer.append("\r");

            terminal.writer().print(outputBuffer);
            terminal.writer().flush();

            lastRender = buffer.toString();
            lastRenderedLines = newLines;
            linesRendered = newLines.length;
            needsRender = false;
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public void write(String view) {
        if (!isRunning) return;

        String string = view.isEmpty() ? " " : view;

        renderLock.lock();
        try {
            buffer.setLength(0);
            buffer.append(string);
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public void showCursor() {
        renderLock.lock();
        try {
            terminal.puts(InfoCmp.Capability.cursor_visible);
            terminal.flush();
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public void hideCursor() {
        renderLock.lock();
        try {
            terminal.puts(InfoCmp.Capability.cursor_invisible);
            terminal.flush();
        } finally {
            renderLock.unlock();
        }
    }

    private void writeToTerminalUnlocked(String value) {
        terminal.writer().print(value);
        terminal.writer().flush();
    }

    private void writeToTerminal(String value) {
        renderLock.lock();
        try {
            writeToTerminalUnlocked(value);
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/standard_renderer.go enableMouseCellMotion behavior.
    public void enableMouseCellMotion() {
        writeToTerminal(Code.EnableMouseCellMotion.value());
    }

    @Override
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/standard_renderer.go disableMouseCellMotion behavior.
    public void disableMouseCellMotion() {
        writeToTerminal(Code.DisableMouseCellMotion.value());
    }

    @Override
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/standard_renderer.go enableMouseAllMotion behavior.
    public void enableMouseAllMotion() {
        writeToTerminal(Code.EnableMouseAllMotion.value());
    }

    @Override
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/standard_renderer.go disableMouseAllMotion behavior.
    public void disableMouseAllMotion() {
        writeToTerminal(Code.DisableMouseAllMotion.value());
    }

    @Override
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/standard_renderer.go enableMouseSGRMode behavior.
    public void enableMouseSGRMode() {
        writeToTerminal(Code.EnableMouseSgrExt.value());
    }

    @Override
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/standard_renderer.go disableMouseSGRMode behavior.
    public void disableMouseSGRMode() {
        writeToTerminal(Code.DisableMouseSgrExt.value());
    }

    @Override
    // Latte extension; no Bubble Tea equivalent.
    public void setMouseCursorText() {
        writeToTerminal(Code.SetMouseTextCursor.value());
    }

    @Override
    // Latte extension; no Bubble Tea equivalent.
    public void setMouseCursorPointer() {
        writeToTerminal(Code.SetMousePointerCursor.value());
    }

    @Override
    // Latte extension; no Bubble Tea equivalent.
    public void resetMouseCursor() {
        writeToTerminal(Code.ResetMouseCursor.value());
    }

    @Override
    public void copyToClipboard(String text) {
        writeToTerminal(Code.copyToClipboard(text));
    }

    @Override
    public void clearScreen() {
        renderLock.lock();
        try {
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.flush();
            repaint();
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public boolean altScreen() {
        return isInAltScreen;
    }

    @Override
    public void enterAltScreen() {
        if (isInAltScreen) return;

        renderLock.lock();
        try {
            if (terminal.getType().equals("dumb")) return;

            terminal.puts(InfoCmp.Capability.enter_ca_mode);
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.puts(InfoCmp.Capability.cursor_home);

            repaint();
            needsRender = true;
            isInAltScreen = true;

            terminal.flush();
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public void exitAltScreen() {
        if (!altScreen()) return;

        renderLock.lock();
        try {
            terminal.puts(InfoCmp.Capability.exit_ca_mode);

            repaint();
            needsRender = true;
            isInAltScreen = false;

            terminal.flush();
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public boolean reportFocus() {
        renderLock.lock();
        try {
            return isReportFocus;
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public void enableReportFocus() {
        renderLock.lock();
        try {
            isReportFocus = true;
            writeToTerminalUnlocked(Code.EnableFocusReporting.value());
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public void disableReportFocus() {
        renderLock.lock();
        try {
            isReportFocus = false;
            writeToTerminalUnlocked(Code.DisableFocusReporting.value());
        } finally {
            renderLock.unlock();
        }
    }

    @Override
    public void notifyModelChanged() {
        this.needsRender = true;
    }

    @Override
    public void repaint() {
        lastRender = "";
        lastRenderedLines = new String[]{};
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg instanceof PrintLineMessage printLineMessage) {
            if (!isInAltScreen) {
                renderLock.lock();
                try {
                    String[] lines = printLineMessage.messageBody().split("\n");
                    queuedMessageLines.addAll(Arrays.asList(lines));
                    needsRender = true;
                    repaint();
                } finally {
                    renderLock.unlock();
                }
            }
        } else if (msg instanceof SetWindowTitleMessage windowTitleMessage) {
            setWindowTitle(windowTitleMessage.title());
        } else if (msg instanceof EnableMouseCellMotionMessage) {
            enableMouseCellMotion();
            enableMouseSGRMode();
        } else if (msg instanceof EnableMouseAllMotionMessage) {
            enableMouseAllMotion();
            enableMouseSGRMode();
        } else if (msg instanceof DisableMouseMessage) {
            disableMouseSGRMode();
            disableMouseCellMotion();
            disableMouseAllMotion();
        } else if (msg instanceof SetMouseCursorTextMessage) {
            setMouseCursorText();
        } else if (msg instanceof SetMouseCursorPointerMessage) {
            setMouseCursorPointer();
        } else if (msg instanceof ResetMouseCursorMessage) {
            resetMouseCursor();
        } else if (msg instanceof CopyToClipboardMessage copyToClipboardMessage) {
            copyToClipboard(copyToClipboardMessage.text());
        } else if (msg instanceof WindowSizeMessage windowSizeMessage) {
            this.width = windowSizeMessage.width();
            this.height = windowSizeMessage.height();
        }
    }

    private void setWindowTitle(String title) {
        terminal.writer().print("\u001b]2;" + title + "\u0007");
    }
}
