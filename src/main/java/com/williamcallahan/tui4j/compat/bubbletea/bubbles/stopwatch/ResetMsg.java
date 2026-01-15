package com.williamcallahan.tui4j.compat.bubbletea.bubbles.stopwatch;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

/**
 * ResetMsg is sent when the stopwatch should reset.
 */
public record ResetMsg(
        int id
) implements Message {
}
