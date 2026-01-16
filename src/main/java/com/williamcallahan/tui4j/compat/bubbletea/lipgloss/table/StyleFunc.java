package com.williamcallahan.tui4j.compat.bubbletea.lipgloss.table;

import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;

/**
 * Port of Lipgloss table StyleFunc.
 * Bubble Tea: lipgloss/table/table.go
 */
@FunctionalInterface
public interface StyleFunc {

    Style apply(int row, int col);
}
