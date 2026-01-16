package com.williamcallahan.tui4j.compat.bubbletea.bubbles.table;

import com.williamcallahan.tui4j.compat.bubbletea.bubbles.key.Binding;

/**
 * Port of Bubbles table KeyMap.
 * Bubble Tea: bubbles/table/keys.go
 */
public class Keys {

    private Binding lineUp;
    private Binding lineDown;
    private Binding pageUp;
    private Binding pageDown;
    private Binding halfPageUp;
    private Binding halfPageDown;
    private Binding gotoTop;
    private Binding gotoBottom;

    public Keys() {
        this.lineUp = new Binding(Binding.withKeys("up", "k"), Binding.withHelp("↑/k", "up"));
        this.lineDown = new Binding(Binding.withKeys("down", "j"), Binding.withHelp("↓/j", "down"));
        this.pageUp = new Binding(Binding.withKeys("b", "pgup"), Binding.withHelp("b/pgup", "page up"));
        this.pageDown = new Binding(Binding.withKeys("f", "pgdown", " "), Binding.withHelp("f/pgdn", "page down"));
        this.halfPageUp = new Binding(Binding.withKeys("u", "ctrl+u"), Binding.withHelp("u", "½ page up"));
        this.halfPageDown = new Binding(Binding.withKeys("d", "ctrl+d"), Binding.withHelp("d", "½ page down"));
        this.gotoTop = new Binding(Binding.withKeys("home", "g"), Binding.withHelp("g/home", "go to start"));
        this.gotoBottom = new Binding(Binding.withKeys("end", "G"), Binding.withHelp("G/end", "go to end"));
    }

    public static Keys defaultKeys() {
        return new Keys();
    }

    public Binding lineUp() {
        return lineUp;
    }

    public Keys lineUp(Binding lineUp) {
        this.lineUp = lineUp;
        return this;
    }

    public Binding lineDown() {
        return lineDown;
    }

    public Keys lineDown(Binding lineDown) {
        this.lineDown = lineDown;
        return this;
    }

    public Binding pageUp() {
        return pageUp;
    }

    public Keys pageUp(Binding pageUp) {
        this.pageUp = pageUp;
        return this;
    }

    public Binding pageDown() {
        return pageDown;
    }

    public Keys pageDown(Binding pageDown) {
        this.pageDown = pageDown;
        return this;
    }

    public Binding halfPageUp() {
        return halfPageUp;
    }

    public Keys halfPageUp(Binding halfPageUp) {
        this.halfPageUp = halfPageUp;
        return this;
    }

    public Binding halfPageDown() {
        return halfPageDown;
    }

    public Keys halfPageDown(Binding halfPageDown) {
        this.halfPageDown = halfPageDown;
        return this;
    }

    public Binding gotoTop() {
        return gotoTop;
    }

    public Keys gotoTop(Binding gotoTop) {
        this.gotoTop = gotoTop;
        return this;
    }

    public Binding gotoBottom() {
        return gotoBottom;
    }

    public Keys gotoBottom(Binding gotoBottom) {
        this.gotoBottom = gotoBottom;
        return this;
    }

    public Binding[] shortHelp() {
        return new Binding[]{lineUp, lineDown};
    }

    public Binding[][] fullHelp() {
        return new Binding[][]{
                {lineUp, lineDown, gotoTop, gotoBottom},
                {pageUp, pageDown, halfPageUp, halfPageDown}
        };
    }
}
