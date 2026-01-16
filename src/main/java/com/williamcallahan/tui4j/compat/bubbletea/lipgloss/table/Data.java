package com.williamcallahan.tui4j.compat.bubbletea.lipgloss.table;

/**
 * Port of Lipgloss table data interface.
 * Bubble Tea: lipgloss/table/rows.go
 */
public interface Data {

    String at(int row, int cell);

    int rows();

    int columns();
}
