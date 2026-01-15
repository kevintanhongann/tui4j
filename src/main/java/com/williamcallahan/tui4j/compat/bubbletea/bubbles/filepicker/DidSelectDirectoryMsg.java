package com.williamcallahan.tui4j.compat.bubbletea.bubbles.filepicker;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

public class DidSelectDirectoryMsg implements Message {

    private final String path;

    public DidSelectDirectoryMsg(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
