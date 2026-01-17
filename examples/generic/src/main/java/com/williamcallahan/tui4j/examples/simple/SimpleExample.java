package com.williamcallahan.tui4j.examples.simple;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;

public class SimpleExample implements Model {
    private final int seconds;

    public SimpleExample() {
        this(5);
    }

    public SimpleExample(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public Command init() {
        return SimpleExample::tick;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            if (key.equals("ctrl+c") || key.equals("q")) {
                return new UpdateResult<>(this, QuitMessage::new);
            }
            return new UpdateResult<>(this, null);
        }

        if (msg instanceof TickMessage) {
            if (seconds <= 1) {
                return new UpdateResult<>(this, QuitMessage::new);
            }
            return new UpdateResult<>(new SimpleExample(seconds - 1), SimpleExample::tick);
        }

        return new UpdateResult<>(this, null);
    }

    @Override
    public String view() {
        return String.format("""
                Hi. This program will exit in %d seconds.

                To quit sooner press ctrl+c or q.
                """, seconds);
    }

    private static Message tick() {
        try {
            Thread.sleep(1000);
            return new TickMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public static void main(String[] args) {
        new Program(new SimpleExample()).run();
    }
}
