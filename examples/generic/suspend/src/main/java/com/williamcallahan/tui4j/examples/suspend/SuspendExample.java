package com.williamcallahan.tui4j.examples.suspend;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.ResumeMessage;

public class SuspendExample implements Model {
    private boolean quitting;
    private boolean suspending;

    public SuspendExample() {
        this.quitting = false;
        this.suspending = false;
    }

    @Override
    public Command init() {
        return null;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof ResumeMessage) {
            suspending = false;
            return UpdateResult.from(this);
        }
        if (msg instanceof KeyPressMessage keyPressMessage) {
            switch (keyPressMessage.key()) {
                case "q", "esc":
                    quitting = true;
                    return new UpdateResult<>(this, QuitMessage::new);
                case "ctrl+c":
                    quitting = true;
                    return new UpdateResult<>(this, QuitMessage::new);
                case "ctrl+z":
                    suspending = true;
                    return new UpdateResult<>(this, () -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        return null;
                    });
            }
        }
        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        if (suspending || quitting) {
            return "";
        }
        return "\nPress ctrl-z to suspend, ctrl+c to interrupt, q, or esc to exit\n";
    }

    public static void main(String[] args) {
        new Program(new SuspendExample()).run();
    }
}
