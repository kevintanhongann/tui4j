package com.williamcallahan.tui4j;

import com.williamcallahan.tui4j.compat.bubbletea.message.BatchMessage;
import com.williamcallahan.tui4j.message.CheckWindowSizeMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.ClearScreenMessage;
import com.williamcallahan.tui4j.message.CopyToClipboardMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.DisableMouseMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.EnableMouseAllMotionMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.EnableMouseCellMotionMessage;
import com.williamcallahan.tui4j.message.OpenUrlMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.PrintLineMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.message.ResetMouseCursorMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.SequenceMessage;
import com.williamcallahan.tui4j.message.SetMouseCursorPointerMessage;
import com.williamcallahan.tui4j.message.SetMouseCursorTextMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.SetWindowTitleMessage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a command that yields a Message.
 * tui4j: src/main/java/com/williamcallahan/tui4j/Command.java
 */
public interface Command {

    Message execute();

    static Command batch(Collection<Command> commands) {
        Command[] filteredCommands = commands.stream()
                .filter(Objects::nonNull)
                .toArray(Command[]::new);
        return () -> new BatchMessage(filteredCommands);
    }

    static Command batch(Command... commands) {
        Command[] filteredCommands = Arrays.stream(commands)
                .filter(Objects::nonNull)
                .toArray(Command[]::new);
        return () -> new BatchMessage(filteredCommands);
    }

    static Command sequence(Command... commands) {
        return () -> new SequenceMessage(commands);
    }

    static Command tick(Duration duration, Function<LocalDateTime, Message> fn) {
        return () -> {
            BlockingQueue<LocalDateTime> queue = new ArrayBlockingQueue<>(1);
            Timer timer = new Timer(true);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    queue.offer(LocalDateTime.now());
                }
            }, duration.toMillis());

            try {
                LocalDateTime time = queue.take();
                timer.cancel();
                return fn.apply(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

    static Command println(Object... arguments) {
        return () -> new PrintLineMessage(Arrays.stream(arguments)
                .map(String::valueOf)
                .collect(Collectors.joining(" ")));
    }

    static Command printf(String template, Object... arguments) {
        return () -> new PrintLineMessage(template.formatted(arguments));
    }

    /**
     * Sets the terminal window title.
     *
     * @param title the title to set
     * @return the command
     */
    static Command setWindowTitle(String title) {
        return () -> new SetWindowTitleMessage(title);
    }

    /**
     * @deprecated Use {@link #setWindowTitle(String)} instead (typo fix).
     */
    @Deprecated(forRemoval = true)
    static Command setWidowTitle(String title) {
        return setWindowTitle(title);
    }

    static Command clearScreen() {
        return ClearScreenMessage::new;
    }

    static Command checkWindowSize() {
        return CheckWindowSizeMessage::new;
    }

    static Command quit() {
        return QuitMessage::new;
    }

    static Command setMouseCursorText() {
        return SetMouseCursorTextMessage::new;
    }

    static Command setMouseCursorPointer() {
        return SetMouseCursorPointerMessage::new;
    }

    static Command resetMouseCursor() {
        return ResetMouseCursorMessage::new;
    }

    static Command enableMouseCellMotion() {
        return EnableMouseCellMotionMessage::new;
    }

    static Command enableMouseAllMotion() {
        return EnableMouseAllMotionMessage::new;
    }

    static Command disableMouse() {
        return DisableMouseMessage::new;
    }

    static Command copyToClipboard(String text) {
        return () -> new CopyToClipboardMessage(text);
    }

    static Command openUrl(String url) {
        return () -> new OpenUrlMessage(url);
    }
}
