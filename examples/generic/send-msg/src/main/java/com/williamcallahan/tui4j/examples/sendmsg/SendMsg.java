package com.williamcallahan.tui4j.examples.sendmsg;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

import java.time.Duration;

public record SendMsg(Duration duration, String food) implements Message {
    @Override
    public String toString() {
        if (duration == null || duration.isZero()) {
            return ".";
        }
        return String.format("Ate %s (%s)", food, formatDuration(duration));
    }

    private String formatDuration(Duration d) {
        long seconds = d.getSeconds();
        long millis = d.toMillis() % 1000;
        if (seconds > 0) {
            return String.format("%d.%03ds", seconds, millis);
        }
        return String.format("%dms", millis);
    }
}
