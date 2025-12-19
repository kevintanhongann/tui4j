package org.flatscrew.latte.message;

import org.flatscrew.latte.Message;

/**
 * Requests resetting the mouse cursor to the terminal default (OSC 22).
 * Latte extension; no Bubble Tea equivalent.
 */
public record ResetMouseCursorMessage() implements Message {
}
