package com.williamcallahan.tui4j.compat.bubbletea.bubbles.stopwatch;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

/**
 * TickMsg is a message that is sent on every timer tick.
 */
public record TickMsg(
        int id,
        int tag
) implements Message {
}
