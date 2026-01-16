package com.williamcallahan.tui4j.compat.bubbletea.bubbles.table;

import com.williamcallahan.tui4j.ansi.TextWidth;
import com.williamcallahan.tui4j.ansi.Truncate;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.KeyMap;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.key.Binding;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Port of Bubbles table.
 * Bubble Tea: bubbles/table/table.go
 */
public class Table implements Model, KeyMap {

    private Keys keys;
    private Styles styles;
    private boolean focused;

    private List<Column> columns = new ArrayList<>();
    private List<Row> rows = new ArrayList<>();
    private int cursor = 0;

    private int width = 80;
    private int height = 20;
    private int start = 0;
    private int end = 0;

    public Table() {
        this.keys = Keys.defaultKeys();
        this.styles = Styles.defaultStyles();
        this.focused = false;
        updateViewport();
    }

    public static Table create() {
        return new Table();
    }

    public Table columns(List<Column> columns) {
        this.columns = columns;
        updateViewport();
        return this;
    }

    public Table columns(Column... columns) {
        this.columns = List.of(columns);
        updateViewport();
        return this;
    }

    public Table rows(List<Row> rows) {
        this.rows = rows;
        if (cursor > rows.size() - 1) {
            cursor = Math.max(0, rows.size() - 1);
        }
        updateViewport();
        return this;
    }

    public Table rows(Row... rows) {
        this.rows = List.of(rows);
        updateViewport();
        return this;
    }

    public Table height(int height) {
        this.height = height;
        updateViewport();
        return this;
    }

    public Table width(int width) {
        this.width = width;
        updateViewport();
        return this;
    }

    public Table focused(boolean focused) {
        this.focused = focused;
        updateViewport();
        return this;
    }

    public Table styles(Styles styles) {
        this.styles = styles;
        updateViewport();
        return this;
    }

    public Table keyMap(Keys keys) {
        this.keys = keys;
        return this;
    }

    @Override
    public Command init() {
        return null;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (!focused) {
            return UpdateResult.from(this);
        }

        if (msg instanceof KeyPressMessage keyPressMessage) {
            if (Binding.matches(keyPressMessage, keys.lineUp())) {
                moveUp(1);
            } else if (Binding.matches(keyPressMessage, keys.lineDown())) {
                moveDown(1);
            } else if (Binding.matches(keyPressMessage, keys.pageUp())) {
                moveUp(end - start);
            } else if (Binding.matches(keyPressMessage, keys.pageDown())) {
                moveDown(end - start);
            } else if (Binding.matches(keyPressMessage, keys.halfPageUp())) {
                moveUp((end - start) / 2);
            } else if (Binding.matches(keyPressMessage, keys.halfPageDown())) {
                moveDown((end - start) / 2);
            } else if (Binding.matches(keyPressMessage, keys.gotoTop())) {
                gotoTop();
            } else if (Binding.matches(keyPressMessage, keys.gotoBottom())) {
                gotoBottom();
            }
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append(headersView()).append("\n");
        sb.append(contentView());
        return sb.toString();
    }

    private String headersView() {
        List<String> headers = new ArrayList<>();
        for (Column col : columns) {
            if (col.width() <= 0) {
                continue;
            }
            String truncated = Truncate.truncate(col.title(), col.width(), "…");
            String rendered = Style.newStyle()
                    .width(col.width())
                    .inline(true)
                    .render(truncated);
            headers.add(styles.header().render(rendered));
        }
        return joinHorizontal(headers.toArray(new String[0]));
    }

    private String contentView() {
        if (rows.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end && i < rows.size(); i++) {
            sb.append(renderRow(i));
            if (i < end - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String renderRow(int rowIndex) {
        List<String> cells = new ArrayList<>();
        Row row = rows.get(rowIndex);

        for (int i = 0; i < columns.size() && i < row.size(); i++) {
            Column col = columns.get(i);
            if (col.width() <= 0) {
                continue;
            }
            String cellValue = row.at(i);
            String truncated = Truncate.truncate(cellValue, col.width(), "…");
            String rendered = Style.newStyle()
                    .width(col.width())
                    .inline(true)
                    .render(truncated);
            cells.add(styles.cell().render(rendered));
        }

        String rowStr = joinHorizontal(cells.toArray(new String[0]));

        if (rowIndex == cursor) {
            return styles.selected().render(rowStr);
        }
        return rowStr;
    }

    private String joinHorizontal(String... parts) {
        if (parts.length == 0) {
            return "";
        }
        if (parts.length == 1) {
            return parts[0];
        }
        String[][] linesParts = new String[parts.length][];
        int maxLines = 0;
        for (int i = 0; i < parts.length; i++) {
            linesParts[i] = parts[i].split("\n");
            maxLines = Math.max(maxLines, linesParts[i].length);
        }
        StringBuilder result = new StringBuilder();
        for (int line = 0; line < maxLines; line++) {
            if (line > 0) {
                result.append("\n");
            }
            for (int i = 0; i < parts.length; i++) {
                if (i > 0) {
                    result.append("");
                }
                if (line < linesParts[i].length) {
                    result.append(linesParts[i][line]);
                } else {
                    int w = TextWidth.measureCellWidth(parts[i]);
                    result.append(" ".repeat(Math.max(0, w)));
                }
            }
        }
        return result.toString();
    }

    private void updateViewport() {
        int viewportHeight = height - headersView().split("\n").length;

        if (cursor >= 0) {
            start = clamp(cursor - viewportHeight, 0, cursor);
        } else {
            start = 0;
        }
        end = clamp(cursor + viewportHeight + 1, cursor, rows.size());
    }

    private int clamp(int value, int low, int high) {
        return Math.max(low, Math.min(value, high));
    }

    public boolean focused() {
        return focused;
    }

    public void focus() {
        this.focused = true;
        updateViewport();
    }

    public void blur() {
        this.focused = false;
        updateViewport();
    }

    public Row selectedRow() {
        if (cursor < 0 || cursor >= rows.size()) {
            return null;
        }
        return rows.get(cursor);
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        if (cursor > rows.size() - 1) {
            cursor = rows.size() - 1;
        }
        updateViewport();
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
        updateViewport();
    }

    public void setWidth(int width) {
        this.width = width;
        updateViewport();
    }

    public void setHeight(int height) {
        this.height = height;
        updateViewport();
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    public int cursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = clamp(cursor, 0, Math.max(0, rows.size() - 1));
        updateViewport();
    }

    public void moveUp(int n) {
        cursor = clamp(cursor - n, 0, Math.max(0, rows.size() - 1));
        if (start == 0) {
            start = Math.max(0, Math.min(cursor, start));
        } else if (start < viewportHeight()) {
            start = clamp(start + n, 0, Math.min(cursor, viewportHeight()));
        } else if (start >= 1) {
            start = clamp(start + n, 1, viewportHeight());
        }
        updateViewport();
    }

    public void moveDown(int n) {
        cursor = clamp(cursor + n, 0, Math.max(0, rows.size() - 1));
        updateViewport();

        if (end == rows.size() && start > 0) {
            start = clamp(start - n, 1, viewportHeight());
        } else if (cursor > (end - start) / 2 && start > 0) {
            start = clamp(start - n, 1, cursor);
        } else if (start > 1) {
        } else if (cursor > start + viewportHeight() - 1) {
            start = clamp(start + 1, 0, 1);
        }
        updateViewport();
    }

    private int viewportHeight() {
        return Math.max(1, height - 1);
    }

    public void gotoTop() {
        moveUp(cursor);
    }

    public void gotoBottom() {
        moveDown(rows.size());
    }

    public void fromValues(String value, String separator) {
        List<Row> rows = new ArrayList<>();
        String[] lines = value.split("\n");
        for (String line : lines) {
            String[] fields = line.split(separator);
            rows.add(new Row(fields));
        }
        setRows(rows);
    }

    public Styles styles() {
        return styles;
    }

    public Keys keyMap() {
        return keys;
    }

    @Override
    public Binding[] shortHelp() {
        return keys.shortHelp();
    }

    @Override
    public Binding[][] fullHelp() {
        return keys.fullHelp();
    }
}
