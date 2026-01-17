package com.williamcallahan.tui4j.examples.progress.staticview;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.WindowSizeMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress.Progress;

import java.time.Duration;
import java.time.LocalDateTime;

public class ProgressStaticExample implements Model {

    private static final int PADDING = 2;
    private static final int MAX_WIDTH = 80;

    private final Progress progress;
    private double percent;
    private int width;

    public ProgressStaticExample() {
        this.progress = new Progress()
                .withWidth(40)
                .withShowPercentage(true)
                .withDefaultGradient();
        this.percent = 0.0;
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
            percent += 0.25;
            if (percent > 1.0) {
                percent = 1.0;
                return new UpdateResult<>(this, QuitMessage::new);
            }
            return new UpdateResult<>(this, tickCmd());
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(" ".repeat(PADDING));
        sb.append(progress.viewAs(percent));
        sb.append("\n\n");
        sb.append(" ".repeat(PADDING));
        sb.append("Press any key to quit");
        return sb.toString();
    }

    private Command tickCmd() {
        return Command.tick(Duration.ofSeconds(1), time -> new TickMsg());
    }

    public static void main(String[] args) {
        new Program(new ProgressStaticExample()).run();
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
