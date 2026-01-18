package com.williamcallahan.tui4j.examples.filepicker;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.filepicker.FilePicker;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.Help;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.KeyMap;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.key.Binding;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePickerExample implements Model {

    private final FilePicker filePicker;
    private final Help help;
    private final FilePickerKeyMap keys;
    private String selectedFile;
    private boolean quitting;
    private String errorMessage;

    public FilePickerExample() {
        this.filePicker = new FilePicker();
        this.filePicker.setAllowedTypes(".mod", ".sum", ".go", ".txt", ".md");
        this.filePicker.setCurrentDirectory(System.getProperty("user.home"));
        this.filePicker.setShowPermissions(true);
        this.filePicker.setShowSize(true);

        this.help = new Help();
        this.keys = new FilePickerKeyMap();
        this.selectedFile = "";
        this.quitting = false;
        this.errorMessage = null;
        this.errorTimestamp = 0;
    }

    @Override
    public Command init() {
        return filePicker.init();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            if (Binding.matches(keyPressMessage, keys.quit())) {
                quitting = true;
                return UpdateResult.from(this, Command.quit());
            }
        }

        UpdateResult<FilePicker> result = filePicker.update(msg);
        filePicker = result.model();

        if (filePicker.didSelectFile(msg)) {
            selectedFile = filePicker.selectedPath();
        }

        return UpdateResult.from(this, result.command());
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  ");

        if (errorMessage != null) {
            Style errorStyle = Style.newStyle()
                    .foreground(Color.color("9"))
                    .italic(true);
            sb.append(errorStyle.render(errorMessage));
        } else if (selectedFile.isEmpty()) {
            sb.append("Pick a file:");
        } else {
            Style selectedStyle = filePicker.styles().selected();
            sb.append("Selected file: ").append(selectedStyle.render(selectedFile));
        }

        sb.append("\n\n");
        sb.append(filePicker.view());
        sb.append("\n");

        if (!quitting) {
            sb.append("\n");
            sb.append(help.render(keys));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        new Program(new FilePickerExample()).run();
    }

    private static final class FilePickerKeyMap implements KeyMap {
        private final Binding up;
        private final Binding down;
        private final Binding home;
        private final Binding end;
        private final Binding pageUp;
        private final Binding pageDown;
        private final Binding open;
        private final Binding back;
        private final Binding select;
        private final Binding toggleHidden;
        private final Binding quit;

        private FilePickerKeyMap() {
            this.up = new Binding(
                    Binding.withKeys("up", "k"),
                    Binding.withHelp("↑/k", "up")
            );
            this.down = new Binding(
                    Binding.withKeys("down", "j"),
                    Binding.withHelp("↓/j", "down")
            );
            this.home = new Binding(
                    Binding.withKeys("home", "g"),
                    Binding.withHelp("g/home", "go to top")
            );
            this.end = new Binding(
                    Binding.withKeys("end", "G"),
                    Binding.withHelp("G/end", "go to bottom")
            );
            this.pageUp = new Binding(
                    Binding.withKeys("pgup", "ctrl+u"),
                    Binding.withHelp("pgup", "page up")
            );
            this.pageDown = new Binding(
                    Binding.withKeys("pgdown", "ctrl+d"),
                    Binding.withHelp("pgdn", "page down")
            );
            this.open = new Binding(
                    Binding.withKeys("right", "l", "enter"),
                    Binding.withHelp("→/l/enter", "open")
            );
            this.back = new Binding(
                    Binding.withKeys("left", "h", "backspace"),
                    Binding.withHelp("←/h/back", "back")
            );
            this.select = new Binding(
                    Binding.withKeys("ctrl+s"),
                    Binding.withHelp("ctrl+s", "select")
            );
            this.toggleHidden = new Binding(
                    Binding.withKeys("."),
                    Binding.withHelp(".", "toggle hidden files")
            );
            this.quit = new Binding(
                    Binding.withKeys("q", "esc", "ctrl+c"),
                    Binding.withHelp("q", "quit")
            );
        }

        private Binding up() {
            return up;
        }

        private Binding down() {
            return down;
        }

        private Binding home() {
            return home;
        }

        private Binding end() {
            return end;
        }

        private Binding pageUp() {
            return pageUp;
        }

        private Binding pageDown() {
            return pageDown;
        }

        private Binding open() {
            return open;
        }

        private Binding back() {
            return back;
        }

        private Binding select() {
            return select;
        }

        private Binding toggleHidden() {
            return toggleHidden;
        }

        private Binding quit() {
            return quit;
        }

        @Override
        public Binding[] shortHelp() {
            return new Binding[]{open, back, select, toggleHidden, quit};
        }

        @Override
        public Binding[][] fullHelp() {
            return new Binding[][]{
                    new Binding[]{up, down, home, end},
                    new Binding[]{pageUp, pageDown},
                    new Binding[]{open, back, select},
                    new Binding[]{toggleHidden, quit}
            };
        }
    }
}
