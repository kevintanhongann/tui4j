package com.williamcallahan.tui4j.compat.bubbletea.bubbles.table;

/**
 * Port of Bubbles table Column.
 * Bubble Tea: bubbles/table/table.go
 */
public record Column(
        String title,
        int width
) {

    public Column {
        if (width < 0) {
            width = 0;
        }
    }

    public Column(String title) {
        this(title, 0);
    }
}
