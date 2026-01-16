package com.williamcallahan.tui4j.compat.bubbletea.bubbles.table;

import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;

/**
 * Port of Bubbles table Styles.
 * Bubble Tea: bubbles/table/table.go
 */
public class Styles {

    private Style header;
    private Style cell;
    private Style selected;

    public Styles() {
        this.header = Style.newStyle().bold(true).padding(0, 1);
        this.cell = Style.newStyle().padding(0, 1);
        this.selected = Style.newStyle().bold(true);
    }

    public static Styles defaultStyles() {
        return new Styles();
    }

    public Style header() {
        return header;
    }

    public Styles header(Style header) {
        this.header = header;
        return this;
    }

    public Style cell() {
        return cell;
    }

    public Styles cell(Style cell) {
        this.cell = cell;
        return this;
    }

    public Style selected() {
        return selected;
    }

    public Styles selected(Style selected) {
        this.selected = selected;
        return this;
    }
}
