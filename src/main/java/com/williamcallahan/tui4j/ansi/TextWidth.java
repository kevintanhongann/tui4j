package com.williamcallahan.tui4j.ansi;

import com.williamcallahan.tui4j.compat.x.ansi.StringWidth;

import java.util.Objects;

/**
 * Measures display width of Unicode text.
 * <p>
 * Re-exports functionality from the canonical port at
 * {@link com.williamcallahan.tui4j.compat.x.ansi.StringWidth}.
 */
public class TextWidth {

    /**
     * Returns the display width of a string in terminal cells.
     * ANSI escape codes are ignored and wide characters are accounted for.
     *
     * @param input the input string (must not be null)
     * @return the cell width
     * @throws NullPointerException if input is null
     */
    public static int measureCellWidth(String input) {
        Objects.requireNonNull(input, "input");
        return StringWidth.stringWidth(input);
    }
}