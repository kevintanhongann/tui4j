package com.williamcallahan.tui4j.examples.creditcardform;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.textinput.TextInput;

import static com.williamcallahan.tui4j.compat.bubbletea.Command.quit;

public class CreditCardFormExample implements Model {

    private static final int CCN = 0;
    private static final int EXP = 1;
    private static final int CVV = 2;

    private final Style inputStyle = Style.newStyle().foreground(Color.color("#FF06B7"));
    private final Style continueStyle = Style.newStyle().foreground(Color.color("#767676"));

    private final TextInput[] inputs;
    private int focused;
    private String focusedButton;
    private String blurredButton;

    public CreditCardFormExample() {
        this.inputs = new TextInput[3];

        TextInput ccnInput = new TextInput();
        ccnInput.setPlaceholder("4505 **** **** 1234");
        ccnInput.focus();
        ccnInput.setCharLimit(20);
        ccnInput.setWidth(30);
        ccnInput.setPrompt("");
        inputs[CCN] = ccnInput;

        TextInput expInput = new TextInput();
        expInput.setPlaceholder("MM/YY ");
        expInput.setCharLimit(5);
        expInput.setWidth(5);
        expInput.setPrompt("");
        inputs[EXP] = expInput;

        TextInput cvvInput = new TextInput();
        cvvInput.setPlaceholder("XXX");
        cvvInput.setCharLimit(3);
        cvvInput.setWidth(5);
        cvvInput.setPrompt("");
        inputs[CVV] = cvvInput;

        this.focused = CCN;
    }

    @Override
    public Command init() {
        this.focusedButton = continueStyle.copy().bold(true).render("[ Continue -> ]");
        this.blurredButton = continueStyle.render("[ Continue -> ]");
        return TextInput::blink;
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            switch (key) {
                case "enter":
                    if (focused == inputs.length - 1) {
                        return UpdateResult.from(this, quit());
                    }
                    nextInput();
                    break;
                case "ctrl+c", "esc":
                    return UpdateResult.from(this, QuitMessage::new);
                case "shift+tab", "ctrl+p":
                    prevInput();
                    break;
                case "tab", "ctrl+n":
                    nextInput();
                    break;
            }

            Command[] cmds = new Command[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                if (i == focused) {
                    cmds[i] = inputs[i].focus();
                } else {
                    inputs[i].blur();
                }
            }
            return UpdateResult.from(this, Command.batch(cmds));
        }

        Command[] cmds = new Command[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            UpdateResult<? extends Model> result = inputs[i].update(msg);
            cmds[i] = result.command();
        }
        return UpdateResult.from(this, Command.batch(cmds));
    }

    private void nextInput() {
        focused = (focused + 1) % inputs.length;
    }

    private void prevInput() {
        focused--;
        if (focused < 0) {
            focused = inputs.length - 1;
        }
    }

    @Override
    public String view() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Total: $21.50:\n\n");
        builder.append(inputStyle.width(30).render("Card Number")).append("\n");
        builder.append(inputs[CCN].view()).append("\n\n");
        builder.append(inputStyle.width(6).render("EXP")).append("  ");
        builder.append(inputStyle.width(6).render("CVV")).append("\n");
        builder.append(inputs[EXP].view()).append("  ");
        builder.append(inputs[CVV].view()).append("\n\n");

        String button = blurredButton;
        if (focused == inputs.length) {
            button = focusedButton;
        }
        builder.append(button).append("\n");

        return builder.toString();
    }

    public static void main(String[] args) {
        new Program(new CreditCardFormExample()).run();
    }
}
