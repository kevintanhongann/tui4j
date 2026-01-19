package com.williamcallahan.tui4j.compat.bubbletea.lipgloss;


import com.williamcallahan.tui4j.ansi.TextWidth;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.TerminalColor;

/**
 * Port of Lip Gloss whitespace rendering.
 * Upstream: github.com/charmbracelet/lipgloss (whitespace.go)
 */
public final class Whitespace {

    private Style style;
    private String chars;

    public Whitespace(Style style) {
        this.style = style;
    }

    /**
     * Port of the whitespace option hook.
     * Upstream: github.com/charmbracelet/lipgloss (whitespace.go)
     */
    public interface WhitespaceOption {
        void apply(Whitespace whitespace);
    }

    /**
     * Port of the whitespace foreground option.
     * Upstream: github.com/charmbracelet/lipgloss (whitespace.go)
     */
    public static WhitespaceOption WithWhitespaceForeground(TerminalColor color) {
        return whitespace -> whitespace.style = whitespace.style.foreground(color);
    }

    /**
     * Port of the whitespace background option.
     * Upstream: github.com/charmbracelet/lipgloss (whitespace.go)
     */
    public static WhitespaceOption WithWhitespaceBackground(TerminalColor color) {
        return whitespace -> whitespace.style = whitespace.style.background(color);
    }

    /**
     * Port of the whitespace character option.
     * Upstream: github.com/charmbracelet/lipgloss (whitespace.go)
     */
    public static WhitespaceOption WithWhitespaceChars(String chars) {
        return whitespace -> whitespace.chars = chars;
    }

    /**
     * Port of the whitespace constructor helper.
     * Upstream: github.com/charmbracelet/lipgloss (whitespace.go)
     */
    public static Whitespace newWhiteSpace(Renderer renderer, WhitespaceOption... options) {
        Whitespace whitespace = new Whitespace(renderer.newStyle());
        for (WhitespaceOption option : options) {
            option.apply(whitespace);
        }
        return whitespace;
    }

    public String render(int width) {
        if (chars == null || chars.isEmpty()) {
            chars = " ";
        }

        int[] codePoints = chars.codePoints().toArray();
        int j = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width;) {
            int cp = codePoints[j];
            String glyph = new String(Character.toChars(cp));
            builder.append(glyph);
            i += TextWidth.measureCellWidth(glyph);
            j = (j + 1) % codePoints.length;
        }

        int shorty = width - TextWidth.measureCellWidth(builder.toString());
        if (shorty > 0) {
            builder.append(" ".repeat(shorty));
        }

        return style.render(builder.toString());
    }

}
