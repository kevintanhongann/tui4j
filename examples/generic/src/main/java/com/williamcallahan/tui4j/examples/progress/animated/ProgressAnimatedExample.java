package com.williamcallahan.tui4j.examples.progress.animated;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.WindowSizeMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress.FrameMsg;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress.Progress;

import java.time.Duration;
import java.time.LocalDateTime;

public class ProgressAnimatedExample implements Model {

    private static final int PADDING = 2;
    private static final int MAX_WIDTH = 80;

    private Progress progress;
    private int width;

    public ProgressAnimatedExample() {
        this.progress = new Progress()
                .withShowPercentage(true)
                .withDefaultGradient();
        this.width = 40;
    }

    @Override
    public Command init() {
        return tickCmd();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage) {
            return new UpdateResult<>(this, QuitMessage::new);
        }

        if (msg instanceof WindowSizeMessage windowSizeMessage) {
            this.width = windowSizeMessage.width() - PADDING * 2 - 4;
            if (this.width > MAX_WIDTH) {
                this.width = MAX_WIDTH;
            }
            progress.setWidth(this.width);
            return UpdateResult.from(this);
        }

        if (msg instanceof TickMsg) {
            if (progress.percent() >= 1.0) {
                return new UpdateResult<>(this, QuitMessage::new);
            }
            Command cmd = progress.incrPercent(0.25);
            return new UpdateResult<>(this, Command.batch(tickCmd(), cmd));
        }

        if (msg instanceof FrameMsg frameMsg) {
            UpdateResult<Progress> result = progress.update(msg);
            this.progress = result.model();
            return UpdateResult.from(this, result.command());
        }

        UpdateResult<Progress> result = progress.update(msg);
        this.progress = result.model();
        return UpdateResult.from(this, result.command());
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(" ".repeat(PADDING));
        sb.append(progress.view());
        sb.append("\n\n");
        sb.append(" ".repeat(PADDING));
        sb.append("Press any key to quit");
        return sb.toString();
    }

    private Command tickCmd() {
        return Command.tick(Duration.ofSeconds(1), time -> new TickMsg());
    }

    public static void main(String[] args) {
        new Program(new ProgressAnimatedExample()).run();
    }
}

class TickMsg implements Message {
    private final LocalDateTime time;

    public TickMsg() {
        this.time = LocalDateTime.now();
    }

    public LocalDateTime time() {
        return time;
    }
}
