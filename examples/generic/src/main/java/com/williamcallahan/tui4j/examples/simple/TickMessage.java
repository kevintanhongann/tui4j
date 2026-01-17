package com.williamcallahan.tui4j.examples.simple;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

import java.time.Instant;

public class TickMessage implements Message {
    private final Instant time;

    public TickMessage() {
        this.time = Instant.now();
    }

    public Instant time() {
        return time;
    }
}
