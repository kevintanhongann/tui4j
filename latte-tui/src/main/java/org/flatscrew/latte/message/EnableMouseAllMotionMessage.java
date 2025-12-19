package org.flatscrew.latte.message;

import org.flatscrew.latte.Message;

/**
 * Enables "all motion" mouse tracking (report motion without a pressed button).
 * Bubble Tea: seeks to replicate charmbracelet/bubbletea/screen.go EnableMouseAllMotion behavior.
 */
public record EnableMouseAllMotionMessage() implements Message {
}
