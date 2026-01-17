package com.williamcallahan.tui4j.examples.spinners;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.Spinner;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.SpinnerType;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.TickMessage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpinnersExample implements Model {

    private static final int TICK_INTERVAL_MS = 100;

    private final List<Spinner> spinners;
    private final Style spinnerStyle;
    private final Style nameStyle;
    private final Style containerStyle;
    private int tickTag;

    public SpinnersExample() {
        this.spinners = new ArrayList<>();
        this.tickTag = 0;

        this.spinnerStyle = Style.newStyle()
                .foreground(Color.color("69"));

        this.nameStyle = Style.newStyle()
                .foreground(Color.color("252"));

        this.containerStyle = Style.newStyle()
                .paddingTop(1)
                .paddingBottom(1)
                .paddingLeft(2)
                .paddingRight(2);

        for (SpinnerType type : SpinnerType.values()) {
            Spinner spinner = new Spinner(type);
            spinner.setStyle(spinnerStyle.copy());
            spinners.add(spinner);
        }
    }

    @Override
    public Command init() {
        return this::tick;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            if (key.equals("ctrl+c") || key.equals("q")) {
                return new UpdateResult<>(this, QuitMessage::new);
            }
            return new UpdateResult<>(this, null);
        }

        if (msg instanceof TickMessage) {
            tickTag++;
            for (int i = 0; i < spinners.size(); i++) {
                Spinner spinner = spinners.get(i);
                UpdateResult<? extends Model> result = spinner.update(msg);
                spinners.set(i, (Spinner) result.model());
            }
            return new UpdateResult<>(this, Command.tick(Duration.ofMillis(TICK_INTERVAL_MS), this::createTickMessage));
        }

        return new UpdateResult<>(this, null);
    }

    private Message createTickMessage(LocalDateTime time) {
        return new TickMessage(time, tickTag, 0);
    }

    private Message tick() {
        return createTickMessage(LocalDateTime.now());
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();

        int cols = 4;
        int rows = (int) Math.ceil((double) spinners.size() / cols);

        for (int row = 0; row < rows; row++) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int col = 0; col < cols; col++) {
                int index = row * cols + col;
                if (index >= spinners.size()) {
                    break;
                }
                Spinner spinner = spinners.get(index);
                String name = SpinnerType.values()[index].name();
                String cell = nameStyle.render(centerString(name, 12)) + " " + spinner.view();
                rowBuilder.append(cell);
                if (col < cols - 1) {
                    rowBuilder.append("  ");
                }
            }
            sb.append(containerStyle.render(rowBuilder.toString())).append("\n");
        }

        sb.append("\n");
        sb.append(nameStyle.render("Press q or ctrl+c to quit\n"));

        return sb.toString();
    }

    private String centerString(String text, int width) {
        int textWidth = stringWidth(text);
        int padding = (width - textWidth) / 2;
        int leftPad = padding;
        int rightPad = width - textWidth - leftPad;
        return " ".repeat(leftPad) + text + " ".repeat(rightPad);
    }

    private int stringWidth(String s) {
        com.williamcallahan.tui4j.ansi.TextWidth tw = new com.williamcallahan.tui4j.ansi.TextWidth();
        return tw.measureCellWidth(s);
    }

    public static void main(String[] args) {
        new Program(new SpinnersExample()).run();
    }
}
