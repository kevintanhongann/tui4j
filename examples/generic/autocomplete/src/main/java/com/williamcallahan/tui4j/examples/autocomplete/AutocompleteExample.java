package com.williamcallahan.tui4j.examples.autocomplete;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.Help;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.KeyMap;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.key.Binding;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.textinput.TextInput;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Color;

import java.util.List;

public class AutocompleteExample implements Model {

    private final TextInput textInput;
    private final Help help;
    private final AutocompleteKeyMap keys;
    private boolean quitting;

    public AutocompleteExample() {
        this.textInput = new TextInput();
        textInput.setPlaceholder("repository");
        textInput.setPrompt("charmbracelet/");
        textInput.setShowSuggestions(true);
        textInput.setCharLimit(50);
        textInput.setWidth(20);
        textInput.focus();

        Style promptStyle = Style.newStyle().foreground(Color.color("63"));
        textInput.setPromptStyle(promptStyle);

        this.help = new Help();
        this.keys = new AutocompleteKeyMap();
        this.quitting = false;
    }

    @Override
    public Command init() {
        String[] sampleRepos = {
            "bubbletea",
            "glow",
            "harmony",
            "kelp",
            "vhs",
            "glamour",
            "tree",
            "soft-serve",
            "wish",
            "neat"
        };
        textInput.setSuggestions(sampleRepos);
        return null;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            if (Binding.matches(keyPressMessage, keys.quit())) {
                quitting = true;
                return UpdateResult.from(this, Command.quit());
            }
            if (Binding.matches(keyPressMessage, keys.complete())) {
                if (!textInput.isEmpty()) {
                    String suggestion = textInput.currentSuggestion();
                    if (!suggestion.isEmpty()) {
                        textInput.setValue(suggestion);
                    }
                }
                return UpdateResult.from(this);
            }
        }

        UpdateResult<TextInput> result = textInput.update(msg);
        this.textInput = result.model();
        return UpdateResult.from(this, result.command());
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pick a Charm repo:\n\n");
        sb.append("  ").append(textInput.view()).append("\n\n");

        if (!quitting) {
            sb.append(help.render(keys));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        new Program(new AutocompleteExample()).run();
    }

    private static final class AutocompleteKeyMap implements KeyMap {
        private final Binding complete;
        private final Binding next;
        private final Binding prev;
        private final Binding quit;

        private AutocompleteKeyMap() {
            this.complete = new Binding(
                    Binding.withKeys("tab"),
                    Binding.withHelp("tab", "complete")
            );
            this.next = new Binding(
                    Binding.withKeys("ctrl+n"),
                    Binding.withHelp("ctrl+n", "next")
            );
            this.prev = new Binding(
                    Binding.withKeys("ctrl+p"),
                    Binding.withHelp("ctrl+p", "prev")
            );
            this.quit = new Binding(
                    Binding.withKeys("esc", "ctrl+c"),
                    Binding.withHelp("esc", "quit")
            );
        }

        private Binding complete() {
            return complete;
        }

        private Binding next() {
            return next;
        }

        private Binding prev() {
            return prev;
        }

        private Binding quit() {
            return quit;
        }

        @Override
        public Binding[] shortHelp() {
            return new Binding[]{complete, next, prev, quit};
        }

        @Override
        public Binding[][] fullHelp() {
            return new Binding[][]{shortHelp()};
        }
    }
}
