package com.williamcallahan.tui4j.ansi;

import com.williamcallahan.tui4j.compat.x.ansi.Ansi;
import com.williamcallahan.tui4j.compat.x.ansi.Strip;

import java.util.Objects;

/**
 * ANSI-aware text truncation and cutting utilities.
 * <p>
 * Re-exports functionality from the canonical port at
 * {@link com.williamcallahan.tui4j.compat.x.ansi}.
 */
public class Truncate {

    /**
     * Truncates a string to a given display width, adding a tail if truncated.
     * ANSI escape codes are preserved and not counted toward width.
     *
     * @param input the input string (must not be null)
     * @param length the maximum cell width
     * @param tail the tail to append if truncated (must not be null)
     * @return the truncated string
     * @throws NullPointerException if input or tail is null
     */
    public static String truncate(String input, int length, String tail) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(tail, "tail");
        return com.williamcallahan.tui4j.compat.x.ansi.Truncate.truncate(input, length, tail);
    }

    /**
     * Truncates a string from the left by removing n display width characters.
     * ANSI escape codes are preserved and not counted toward width.
     *
     * @param input the input string (must not be null)
     * @param n the number of cells to remove from the left
     * @param prefix the prefix to prepend after truncation (must not be null)
     * @return the truncated string
     * @throws NullPointerException if input or prefix is null
     */
    public static String truncateLeft(String input, int n, String prefix) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(prefix, "prefix");
        return com.williamcallahan.tui4j.compat.x.ansi.Truncate.truncateLeft(input, n, prefix);
    }

    /**
     * Cuts a string to display a portion from left to right positions.
     * ANSI escape codes within the visible region are preserved.
     *
     * @param input the input string (must not be null)
     * @param left start position (inclusive)
     * @param right end position (exclusive)
     * @return the cut string
     * @throws NullPointerException if input is null
     */
    public static String cut(String input, int left, int right) {
        Objects.requireNonNull(input, "input");
        return com.williamcallahan.tui4j.compat.x.ansi.Cut.cut(input, left, right);
    }

    /**
     * Removes all ANSI escape codes from a string.
     *
     * @param input the input string (must not be null)
     * @return the string with ANSI codes removed
     * @throws NullPointerException if input is null
     */
    public static String strip(String input) {
        Objects.requireNonNull(input, "input");
        return Strip.strip(input);
    }

    /**
     * Returns the expected byte length of a UTF-8 sequence given its first byte.
     *
     * @param b the first byte of a UTF-8 sequence
     * @return the expected byte length (1-4)
     * @deprecated Use {@link Ansi#utf8ByteLength(byte)} instead
     */
    @Deprecated
    public static int utf8ByteLen(byte b) {
        return Ansi.utf8ByteLength(b);
    }
}
