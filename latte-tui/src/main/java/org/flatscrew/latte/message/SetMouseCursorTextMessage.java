package org.flatscrew.latte.message;

import org.flatscrew.latte.Message;

/**
 * Requests setting the mouse cursor to an I-beam shape (OSC 22).
 * Latte extension; no Bubble Tea equivalent.
 */
public record SetMouseCursorTextMessage() implements Message {
}
