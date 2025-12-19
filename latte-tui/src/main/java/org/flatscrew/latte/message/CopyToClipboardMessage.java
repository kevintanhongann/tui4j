package org.flatscrew.latte.message;

import org.flatscrew.latte.Message;

/**
 * Requests copying text to the system clipboard (OSC 52).
 */
public record CopyToClipboardMessage(String text) implements Message {
}

