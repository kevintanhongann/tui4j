package com.williamcallahan.tui4j.examples.tableresize;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.table.Column;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.table.Row;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.table.Table;
import com.williamcallahan.tui4j.compat.bubbletea.message.WindowSizeMessage;

import java.util.List;

/**
 * Example program for table component with dynamic resizing.
 * tui4j: examples/generic/src/main/java/com/williamcallahan/tui4j/examples/tableresize/TableResizeExample.java
 */
public class TableResizeExample implements Model {

    private Table table;
    private int cursorColumn = 0;
    private String statusMessage = "Resize the window to see the table adapt";

    public TableResizeExample() {
        table = Table.create()
                .columns(List.of(
                        new Column("File", 0),
                        new Column("Size", 12),
                        new Column("Modified", 25),
                        new Column("Permissions", 15)
                ))
                .rows(List.of(
                        new Row("Documents/report.pdf", "2.4 MB", "2024-01-15 10:30", "rw-r--r--"),
                        new Row("Photos/vacation.jpg", "4.1 MB", "2024-01-14 15:22", "rw-r--r--"),
                        new Row("Music/track.mp3", "3.7 MB", "2024-01-13 09:45", "rw-------"),
                        new Row("Projects/app", "-", "2024-01-16 08:00", "rwxr-xr-x"),
                        new Row("Downloads/installer.exe", "156 MB", "2024-01-12 14:30", "rw-r--r--"),
                        new Row("Notes/todo.txt", "1.2 KB", "2024-01-16 11:00", "rw-r--r--"),
                        new Row("Backup/backup.zip", "1.2 GB", "2024-01-10 03:00", "rw-------+")
                ))
                .focused(true);
    }

    @Override
    public Command init() {
        return null;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            String charStr = keyPressMessage.key();
            if ("q".equals(charStr) || "esc".equals(charStr)) {
                return UpdateResult.from(this, QuitMessage::new);
            }
            if ("h".equals(charStr)) {
                if (cursorColumn > 0) {
                    Column col = table.getColumns().get(cursorColumn);
                    List<Column> cols = table.getColumns();
                    cols.set(cursorColumn, new Column(col.title(), Math.max(0, col.width() - 2)));
                    table.columns(cols);
                    cursorColumn--;
                    statusMessage = "Column " + col.title() + " width decreased";
                }
            }
            if ("l".equals(charStr)) {
                if (cursorColumn < table.getColumns().size() - 1) {
                    Column col = table.getColumns().get(cursorColumn);
                    List<Column> cols = table.getColumns();
                    cols.set(cursorColumn, new Column(col.title(), col.width() + 2));
                    table.columns(cols);
                    statusMessage = "Column " + col.title() + " width increased";
                }
            }
            if ("c".equals(charStr)) {
                cursorColumn = (cursorColumn + 1) % table.getColumns().size();
                statusMessage = "Selected column: " + table.getColumns().get(cursorColumn).title();
            }
        } else if (msg instanceof WindowSizeMessage windowSizeMessage) {
            table.setWidth(windowSizeMessage.width());
            table.setHeight(windowSizeMessage.height() - 6);
        }

        UpdateResult<? extends Model> result = table.update(msg);
        return UpdateResult.from(this, result.command());
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resizable Table Demo\n");
        sb.append("─".repeat(70)).append("\n");
        sb.append(table.view()).append("\n");
        sb.append("─".repeat(70)).append("\n");
        sb.append("Controls: ").append("\n");
        sb.append("  ↑/↓    Navigate rows").append("\n");
        sb.append("  h/l    Decrease/Increase selected column width").append("\n");
        sb.append("  c      Cycle through columns").append("\n");
        sb.append("  g/home Go to top, G/end go to bottom").append("\n");
        sb.append("  q/esc  Quit").append("\n");
        sb.append("─".repeat(70)).append("\n");
        sb.append("Status: ").append(statusMessage).append("\n");
        sb.append("Selected column: ").append(cursorColumn).append(" (").append(table.getColumns().get(cursorColumn).title()).append(")");
        return sb.toString();
    }

    public static void main(String[] args) {
        new Program(new TableResizeExample()).run();
    }
}
