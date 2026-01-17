package com.williamcallahan.tui4j.examples.help;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.Help;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.help.KeyMap;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.key.Binding;

public class HelpExample implements Model {

    private final Help help;
    private final HelpKeyMap keys;
    private boolean showFullHelp;

    public HelpExample() {
        this.help = new Help();
        this.keys = new HelpKeyMap();
        this.showFullHelp = false;
    }

    @Override
    public Command init() {
        return null;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            if (Binding.matches(keyPressMessage, keys.toggleFullHelp())) {
                showFullHelp = !showFullHelp;
                help.setShowAll(showFullHelp);
                return UpdateResult.from(this);
            }
            if (Binding.matches(keyPressMessage, keys.quit())) {
                return UpdateResult.from(this, Command.quit());
            }
        }
        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append("This is a simple example demonstrating the help component.\n\n");
        sb.append("Press ").append(keys.toggleFullHelp().help().key()).append(" to toggle full help.\n\n");
        sb.append(help.render(keys));
        return sb.toString();
    }

    private static final class HelpKeyMap implements KeyMap {
        private final Binding toggleFullHelp;
        private final Binding quit;

        private HelpKeyMap() {
            this.toggleFullHelp = new Binding(
                    Binding.withKeys("h", "?"),
                    Binding.withHelp("h/?", "toggle full help")
            );
            this.quit = new Binding(
                    Binding.withKeys("q", "ctrl+c"),
                    Binding.withHelp("q/ctrl+c", "quit")
            );
        }

        public Binding toggleFullHelp() {
            return toggleFullHelp;
        }

        public Binding quit() {
            return quit;
        }

        @Override
        public Binding[] shortHelp() {
            return new Binding[]{
                    toggleFullHelp,
                    quit
            };
        }

        @Override
        public Binding[][] fullHelp() {
            return new Binding[][]{
                    new Binding[]{toggleFullHelp},
                    new Binding[]{quit}
            };
        }
    }

    public static void main(String[] args) {
        new Program(new HelpExample()).run();
    }
}
