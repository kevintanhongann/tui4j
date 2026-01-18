package com.williamcallahan.tui4j.examples.debounce;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.Spinner;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.SpinnerType;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;

import java.time.Duration;

public class DebounceExample implements Model {

    private static final Duration DEBOUNCE_DURATION = Duration.ofSeconds(1);

    private int tag;
    private boolean quitting;
    private Spinner spinner;

    public DebounceExample() {
        this.tag = 0;
        this.quitting = false;
        this.spinner = new Spinner(SpinnerType.DOTS);
        this.spinner.setStyle(com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style.newStyle()
                .foregroundColor(240));
    }

    @Override
    public Command init() {
        return Command.none();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            tag++;
        return UpdateResult.from(this,
                Command.batch(
                        Command.tick(DEBOUNCE_DURATION, __ -> new ExitMsg(tag)),
                        spinner::tick));
        }

        if (msg instanceof ExitMsg exitMsg) {
            if (exitMsg.tag() == tag) {
                quitting = true;
                return UpdateResult.from(this, Command.quit());
            }
        }

        if (spinner.update(msg).command() != null) {
            UpdateResult<Spinner> spinnerResult = spinner.update(msg);
            spinner = spinnerResult.model();
            return UpdateResult.from(this, spinnerResult.command());
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        String status = spinner.view() + " Waiting for debounce...";
        return "Key presses: " + tag +
                "\n" + status +
                "\n\nTo exit press any key, then wait for one second without pressing anything.";
    }

    public static void main(String[] args) {
        new Program(new DebounceExample()).run();
    }

    private record ExitMsg(int tag) implements Message {
    }
}
