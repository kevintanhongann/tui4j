package com.williamcallahan.tui4j.examples.sendmsg;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.Spinner;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.SpinnerType;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.TickMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.Duration;

public class SendMsgExample implements Model {

    private static final int MAX_RESULTS = 5;

    private final Spinner spinner;
    private final List<SendMsg> results;
    private boolean quitting;
    private Program program;

    public SendMsgExample() {
        this.spinner = new Spinner(SpinnerType.DOT);
        this.results = new ArrayList<>(MAX_RESULTS);
    }

    @Override
    public Command init() {
        return spinner.init();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage) {
            quitting = true;
            return new UpdateResult<>(this, QuitMessage::new);
        }

        if (msg instanceof SendMsg result) {
            results.add(result);
            while (results.size() > MAX_RESULTS) {
                results.remove(0);
            }
            return new UpdateResult<>(this, null);
        }

        if (msg instanceof TickMessage) {
            UpdateResult<Spinner> result = spinner.update(msg);
            return UpdateResult.from(this, result.command());
        }

        return new UpdateResult<>(this, null);
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();

        if (quitting) {
            sb.append("That's all for today!\n\n");
        } else {
            sb.append(spinner.view()).append(" Eating food...\n\n");
        }

        for (int i = 0; i < results.size(); i++) {
            SendMsg result = results.get(i);
            sb.append(result.toString()).append("\n");
        }

        if (!quitting) {
            sb.append("\nPress any key to exit");
        }

        return sb.toString();
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    private static final String[] FOODS = {
        "an apple", "a pear", "a gherkin", "a party gherkin",
        "a kohlrabi", "some spaghetti", "tacos", "a currywurst", "some curry",
        "a sandwich", "some peanut butter", "some cashews", "some ramen"
    };

    private static final Random RANDOM = new Random();

    private static String randomFood() {
        return FOODS[RANDOM.nextInt(FOODS.length)];
    }

    public static void main(String[] args) {
        SendMsgExample model = new SendMsgExample();
        Program program = new Program(model);
        model.setProgram(program);

        new Thread(() -> {
            while (model.program != null && model.program.isRunning()) {
                try {
                    int delayMs = RANDOM.nextInt(900) + 100;
                    Thread.sleep(delayMs);
                    if (model.program != null && model.program.isRunning()) {
                        model.program.send(new SendMsg(Duration.ofMillis(delayMs), randomFood()));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();

        program.run();
    }
}
