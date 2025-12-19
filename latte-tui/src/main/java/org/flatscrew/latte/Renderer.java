package org.flatscrew.latte;

public interface Renderer {
    // Start the renderer.
    void start();
    void stop();

    void write(String view);
    void repaint();
    void clearScreen();

    boolean altScreen();
    void enterAltScreen();
    void exitAltScreen();

    void showCursor();
    void hideCursor();

    // enableMouseCellMotion enables mouse click, release, wheel and motion
    // events if a mouse button is pressed (i.e., drag events).
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/renderer.go enableMouseCellMotion behavior.
    default void enableMouseCellMotion() {
    }
    // disableMouseCellMotion disables Mouse Cell Motion tracking.
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/renderer.go disableMouseCellMotion behavior.
    default void disableMouseCellMotion() {
    }

    // enableMouseAllMotion enables mouse click, release, wheel and motion
    // events, regardless of whether a mouse button is pressed. Many modern
    // terminals support this, but not all.
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/renderer.go enableMouseAllMotion behavior.
    void enableMouseAllMotion();
    // disableMouseAllMotion disables All Motion mouse tracking.
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/renderer.go disableMouseAllMotion behavior.
    void disableMouseAllMotion();

    // enableMouseSGRMode enables mouse extended mode (SGR).
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/renderer.go enableMouseSGRMode behavior.
    void enableMouseSGRMode();
    // disableMouseSGRMode disables mouse extended mode (SGR).
    // Bubble Tea: seeks to replicate charmbracelet/bubbletea/renderer.go disableMouseSGRMode behavior.
    void disableMouseSGRMode();

    // setMouseCursorText changes mouse pointer to I-beam shape (OSC 22).
    // Latte extension; no Bubble Tea equivalent.
    default void setMouseCursorText() {
    }

    // setMouseCursorPointer changes mouse pointer to hand shape (OSC 22).
    // Latte extension; no Bubble Tea equivalent.
    default void setMouseCursorPointer() {
    }

    // resetMouseCursor resets mouse pointer to default shape (OSC 22).
    // Latte extension; no Bubble Tea equivalent.
    default void resetMouseCursor() {
    }

    // copyToClipboard copies text to system clipboard (OSC 52).
    default void copyToClipboard(String text) {
    }

    // reportFocus returns whether reporting focus events is enabled.
    boolean reportFocus();
    // enableReportFocus reports focus events to the program.
    void enableReportFocus();
    // disableReportFocus stops reporting focus events to the program.
    void disableReportFocus();

    void notifyModelChanged();

    void handleMessage(Message msg);
}
