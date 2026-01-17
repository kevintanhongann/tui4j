package com.williamcallahan.tui4j.examples.tuidemoncombo;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.Spinner;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.SpinnerType;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.TickMessage;

import java.time.Duration;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaemonComboExample implements Model {

    private static final Logger logger = Logger.getLogger(DaemonComboExample.class.getName());

    private final Spinner spinner;
    private final Deque<Result> results;
    private final Random random;
    private boolean quitting;

    private static final int SHOW_LAST_RESULTS = 5;
    private static final Style HELP_STYLE = Style.newStyle().foreground(Color.color("241"));
    private static final Style MAIN_STYLE = Style.newStyle().marginLeft(1);

    public DaemonComboExample() {
        this.spinner = new Spinner(SpinnerType.DOT).setStyle(
                Style.newStyle().foreground(Color.color("206"))
        );
        this.results = new ArrayDeque<>(SHOW_LAST_RESULTS + 1);
        this.random = new Random();
    }

    @Override
    public Command init() {
        logger.info("Starting work...");
        return Command.batch(spinner.init(), runPretendProcess());
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage) {
            quitting = true;
            return new UpdateResult<>(this, QuitMessage::new);
        }

        if (msg instanceof ProcessFinishedMsg processFinishedMsg) {
            Duration duration = processFinishedMsg.duration();
            Result res = new Result(randomEmoji(), duration);
            logger.info(res.emoji() + " Job finished in " + res.duration());

            results.addLast(res);
            if (results.size() > SHOW_LAST_RESULTS) {
                results.removeFirst();
            }

            return UpdateResult.from(this, runPretendProcess());
        }

        if (msg instanceof TickMessage) {
            UpdateResult<? extends Model> updateResult = spinner.update(msg);
            return UpdateResult.from(this, updateResult.command());
        }

        return UpdateResult.from(this, null);
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(spinner.view()).append(" Doing some work...\n\n");

        for (Result res : results) {
            sb.append(res.emoji()).append(" Job finished in ").append(res.duration()).append("\n");
        }

        for (int i = results.size(); i < SHOW_LAST_RESULTS; i++) {
            sb.append("........................\n");
        }

        sb.append("\n");
        sb.append(HELP_STYLE.render("Press any key to exit\n"));

        if (quitting) {
            sb.append("\n");
        }

        return MAIN_STYLE.render(sb.toString());
    }

    private Command runPretendProcess() {
        return () -> {
            try {
                Duration pause = Duration.ofMillis(100 + random.nextInt(900));
                Thread.sleep(pause.toMillis());
                return new ProcessFinishedMsg(pause);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        };
    }

    private String randomEmoji() {
        String[] emojis = {"🍦", "🧋", "🍡", "🤠", "👾", "😭", "🦊", "🐯", "🦆", "🥨", "🎏", "🍔", "🍒", "🍥", "🎮", "📦", "🦁", "🐶", "🐸", "🍕", "🥐", "🧲", "🚒", "🥇", "🏆", "🌽"};
        return emojis[random.nextInt(emojis.length)];
    }

    public static void main(String[] args) {
        boolean daemonMode = Arrays.asList(args).contains("--daemon");

        if (daemonMode) {
            runDaemon();
        } else {
            runTUI();
        }
    }

    private static void runTUI() {
        disableLoggingForTUI();
        Program program = new Program(new DaemonComboExample());
        try {
            program.run();
        } catch (Exception e) {
            System.err.println("Error starting program: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runDaemon() {
        System.out.println("Running in daemon mode (headless)...");
        Random random = new Random();
        try {
            while (true) {
                Duration pause = Duration.ofMillis(100 + random.nextInt(900));
                Thread.sleep(pause.toMillis());
                String emoji = getRandomEmoji(random);
                System.out.println(emoji + " Job finished in " + pause);
            }
        } catch (InterruptedException e) {
            System.out.println("Daemon stopped.");
        }
    }

    private static String getRandomEmoji(Random random) {
        String[] emojis = {"🍦", "🧋", "🍡", "🤠", "👾", "😭", "🦊", "🐯", "🦆", "🥨", "🎏", "🍔", "🍒", "🍥", "🎮", "📦", "🦁", "🐶", "🐸", "🍕", "🥐", "🧲", "🚒", "🥇", "🏆", "🌽"};
        return emojis[random.nextInt(emojis.length)];
    }

    private static void disableLoggingForTUI() {
        Logger rootLogger = Logger.getLogger("");
        for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.WARNING);
        }
        Logger.getLogger(DaemonComboExample.class.getName()).setLevel(Level.INFO);
    }

    private record Result(String emoji, Duration duration) {}

    private record ProcessFinishedMsg(Duration duration) implements Message {}
}
