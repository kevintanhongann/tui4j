package org.flatscrew.latte.message;

import org.flatscrew.latte.Message;

/**
 * Disables mouse tracking (motion modes and SGR extended mouse mode).
 * Bubble Tea: seeks to replicate charmbracelet/bubbletea/screen.go DisableMouse behavior.
 */
public record DisableMouseMessage() implements Message {
}
