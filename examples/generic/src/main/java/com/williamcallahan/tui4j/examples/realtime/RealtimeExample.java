package com.williamcallahan.tui4j.examples.realtime;

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

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class RealtimeExample implements Model {

    private final Spinner spinner;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Random random = new Random();
    private int eventCount = 0;
    private boolean quitting = false;
    private Program program;

    public RealtimeExample() {
        this.spinner = new Spinner(SpinnerType.DOT);
    }

    @Override
    public Command init() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (running.get()) {
                try {
                    int delay = random.nextInt(901) + 100;
                    Thread.sleep(delay);
                    if (running.get() && program != null) {
                        program.send(RealtimeMsg.ACTIVITY);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        executor.shutdown();

        return spinner.init();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            if ("q".equals(key) || "Q".equals(key)) {
                quitting = true;
                running.set(false);
                return new UpdateResult<>(this, QuitMessage::new);
            }
        }

        if (msg == RealtimeMsg.ACTIVITY) {
            eventCount++;
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
        String spinnerView = spinner.view();
        String content = String.format("  %s Events received: %d", spinnerView, eventCount);

        if (quitting) {
            return content + "\n\n";
        }
        return content + "\n\n  Press any key to exit\n\n";
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public static void main(String[] args) {
        RealtimeExample model = new RealtimeExample();
        Program program = new Program(model);
        model.setProgram(program);
        program.run();
    }
}
