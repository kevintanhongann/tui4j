package org.flatscrew.latte.message;

import org.flatscrew.latte.Message;

/**
 * Enables "cell motion" mouse tracking (report motion while a button is pressed).
 * Bubble Tea: seeks to replicate charmbracelet/bubbletea/screen.go EnableMouseCellMotion behavior.
 */
public record EnableMouseCellMotionMessage() implements Message {
}
