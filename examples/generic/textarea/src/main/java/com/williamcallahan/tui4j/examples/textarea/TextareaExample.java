package com.williamcallahan.tui4j.examples.textarea;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.input.key.KeyType;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.textarea.Textarea;

public class TextareaExample implements Model {

    private Textarea textarea;

    public TextareaExample() {
        this.textarea = new Textarea();
        textarea.setPlaceholder("Once upon a time...");
        textarea.focus();
    }

    @Override
    public Command init() {
        return Textarea::blink;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            if (KeyType.keyESC == keyPressMessage.type()) {
                if (textarea.focused()) {
                    textarea.blur();
                }
            } else if (KeyType.keyCtrlC == keyPressMessage.type()) {
                return UpdateResult.from(this, QuitMessage::new);
            } else {
                if (!textarea.focused()) {
                    textarea.focus();
                }
            }
        }

        UpdateResult<? extends Model> updateResult = textarea.update(msg);
        return UpdateResult.from(this, updateResult.command());
    }

    @Override
    public String view() {
        return "Tell me a story.\n\n%s\n\n(ctrl+c to quit)\n".formatted(textarea.view());
    }

    public static void main(String[] args) {
        new Program(new TextareaExample()).run();
    }
}
