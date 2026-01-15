package com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

public record SetPercentMsg(double percent) implements Message {
}
