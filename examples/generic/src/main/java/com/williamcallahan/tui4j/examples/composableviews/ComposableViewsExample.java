package com.williamcallahan.tui4j.examples.composableviews;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Position;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.join.HorizontalJoinDecorator;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.Spinner;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.SpinnerType;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.timer.Timer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ComposableViewsExample implements Model {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(1);

    private ModelState state;
    private Timer timer;
    private Spinner spinner;
    private int spinnerIndex;

    private static final SpinnerType[] SPINNERS = {
            SpinnerType.LINE,
            SpinnerType.DOT,
            SpinnerType.MINI_DOT,
            SpinnerType.JUMP,
            SpinnerType.PULSE,
            SpinnerType.POINTS,
            SpinnerType.GLOBE,
            SpinnerType.MOON,
            SpinnerType.MONKEY,
    };

    private static final Style MODEL_STYLE = Style.newStyle()
            .width(15)
            .height(5);

    private static final Style FOCUSED_MODEL_STYLE = Style.newStyle()
            .width(15)
            .height(5);

    private static final Style SPINNER_STYLE = Style.newStyle();

    private static final Style HELP_STYLE = Style.newStyle();

    public ComposableViewsExample() {
        this.state = ModelState.TIMER_VIEW;
        this.timer = new Timer(DEFAULT_TIMEOUT);
        this.spinner = createSpinner();
    }

    private Spinner createSpinner() {
        Spinner s = new Spinner(SPINNERS[spinnerIndex]);
        s.setStyle(SPINNER_STYLE);
        return s;
    }

    @Override
    public Command init() {
        return Command.batch(timer.init(), spinner.init());
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        List<Command> commands = new ArrayList<>();

        if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            if (key.equals("ctrl+c") || key.equals("q")) {
                return UpdateResult.from(this, Command.quit());
            }
            if (key.equals("tab")) {
                if (state == ModelState.TIMER_VIEW) {
                    state = ModelState.SPINNER_VIEW;
                } else {
                    state = ModelState.TIMER_VIEW;
                }
            }
            if (key.equals("n")) {
                if (state == ModelState.TIMER_VIEW) {
                    timer = new Timer(DEFAULT_TIMEOUT);
                    commands.add(timer.init());
                } else {
                    nextSpinner();
                    commands.add(spinner.init());
                }
            }
        }

        if (msg instanceof com.williamcallahan.tui4j.compat.bubbletea.bubbles.timer.TickMessage) {
            UpdateResult<Timer> result = timer.update(msg);
            timer = result.model();
            if (result.command() != null) {
                commands.add(result.command());
            }
        } else if (msg instanceof com.williamcallahan.tui4j.compat.bubbletea.bubbles.spinner.TickMessage) {
            UpdateResult<Spinner> result = spinner.update(msg);
            spinner = result.model();
            if (result.command() != null) {
                commands.add(result.command());
            }
        } else if (msg instanceof Message) {
            if (state == ModelState.SPINNER_VIEW) {
                UpdateResult<Spinner> result = spinner.update(msg);
                spinner = result.model();
                if (result.command() != null) {
                    commands.add(result.command());
                }
            } else {
                UpdateResult<Timer> result = timer.update(msg);
                timer = result.model();
                if (result.command() != null) {
                    commands.add(result.command());
                }
            }
        }

        return UpdateResult.from(this, Command.batch(commands.toArray(new Command[0])));
    }

    private void nextSpinner() {
        spinnerIndex++;
        if (spinnerIndex >= SPINNERS.length) {
            spinnerIndex = 0;
        }
        spinner = createSpinner();
    }

    @Override
    public String view() {
        String timerView = timer.view();
        String spinnerView = spinner.view();

        String left;
        String right;
        if (state == ModelState.TIMER_VIEW) {
            left = FOCUSED_MODEL_STYLE.render(String.format("%4s", timerView));
            right = MODEL_STYLE.render(String.format("%4s", spinnerView));
        } else {
            left = MODEL_STYLE.render(String.format("%4s", timerView));
            right = FOCUSED_MODEL_STYLE.render(String.format("%4s", spinnerView));
        }

        String views = HorizontalJoinDecorator.joinHorizontal(Position.Top, left, right);

        String focusedModelName = state == ModelState.TIMER_VIEW ? "timer" : "spinner";
        String help = HELP_STYLE.render(String.format("\ntab: focus next • n: new %s • q: exit\n", focusedModelName));

        return views + help;
    }

    public static void main(String[] args) {
        new Program(new ComposableViewsExample()).run();
    }

    private enum ModelState {
        TIMER_VIEW,
        SPINNER_VIEW
    }
}
