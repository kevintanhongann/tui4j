package com.williamcallahan.tui4j.compat.bubbletea.message;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

/**
 * Message sent when a process execution completes.
 * Bubble Tea: bubbletea/exec.go
 */
public record ExecCompletedMessage(int exitCode, Throwable error) implements Message {

    public boolean success() {
        return error == null && exitCode == 0;
    }

    public String errorMessage() {
        if (error != null) {
            return error.getMessage();
        }
        if (exitCode != 0) {
            return "Process exited with code " + exitCode;
        }
        return null;
    }
}
