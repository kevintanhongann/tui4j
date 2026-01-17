package com.williamcallahan.tui4j.examples.preventquit;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.textarea.Textarea;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.Help;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.KeyMap;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.key.Binding;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.cursor.Cursor;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.border.StandardBorder;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;
import com.williamcallahan.tui4j.compat.bubbletea.input.key.KeyType;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;

public class PreventQuitExample implements Model {

    private static final Style CHOICE_STYLE = Style.newStyle().paddingLeft(1).foreground(Color.color("241"));
    private static final Style SAVE_TEXT_STYLE = Style.newStyle().foreground(Color.color("170"));
    private static final Style QUIT_VIEW_STYLE = Style.newStyle()
            .padding(1)
            .border(StandardBorder.RoundedBorder)
            .borderForeground(Color.color("170"));

    private Textarea textarea;
    private Help help;
    private PreventQuitKeyMap keymap;
    private String saveText;
    private boolean hasChanges;
    private boolean quitting;

    public PreventQuitExample() {
        this.textarea = new Textarea();
        textarea.setPlaceholder("Only the best words");
        textarea.focus();
        textarea.setWidth(40);
        textarea.setHeight(6);

        this.help = new Help();
        this.keymap = new PreventQuitKeyMap();
        this.saveText = "";
        this.hasChanges = false;
        this.quitting = false;
    }

    @Override
    public Command init() {
        return Cursor::blink;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (quitting) {
            return updatePromptView(msg);
        }
        return updateTextView(msg);
    }

    private UpdateResult<? extends Model> updateTextView(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            saveText = "";

            if (Binding.matches(keyPressMessage, keymap.save())) {
                saveText = "Changes saved!";
                hasChanges = false;
                return UpdateResult.from(this);
            }

            if (Binding.matches(keyPressMessage, keymap.quit())) {
                quitting = true;
                return UpdateResult.from(this, QuitMessage::new);
            }

            if (keyPressMessage.type() == KeyType.KeyRunes) {
                hasChanges = true;
            }
        }

        UpdateResult<? extends Model> taResult = textarea.update(msg);
        return UpdateResult.from(this, taResult.command());
    }

    private UpdateResult<? extends Model> updatePromptView(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            if (Binding.matches(keyPressMessage, keymap.quit()) || keyPressMessage.key().equals("y")) {
                hasChanges = false;
                return UpdateResult.from(this, QuitMessage::new);
            }
            quitting = false;
        }
        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        if (quitting) {
            if (hasChanges) {
                String text = "You have unsaved changes. Quit without saving? [yn]";
                return CHOICE_STYLE.render(text);
            }
            return "Very important, thank you\n";
        }

        String helpView = help.render(keymap);

        return "\nType some important things.\n\n" +
                textarea.view() +
                "\n\n" +
                SAVE_TEXT_STYLE.render(saveText) +
                "\n " +
                helpView +
                "\n";
    }

    private static final class PreventQuitKeyMap implements KeyMap {
        private final Binding save;
        private final Binding quit;

        private PreventQuitKeyMap() {
            this.save = new Binding(
                    Binding.withKeys("ctrl+s"),
                    Binding.withHelp("ctrl+s", "save")
            );
            this.quit = new Binding(
                    Binding.withKeys("esc", "ctrl+c"),
                    Binding.withHelp("esc", "quit")
            );
        }

        public Binding save() {
            return save;
        }

        public Binding quit() {
            return quit;
        }

        @Override
        public Binding[] shortHelp() {
            return new Binding[]{save, quit};
        }

        @Override
        public Binding[][] fullHelp() {
            return new Binding[][]{
                    new Binding[]{save},
                    new Binding[]{quit}
            };
        }
    }

    public static void main(String[] args) {
        new Program(new PreventQuitExample()).run();
    }
}
