package com.williamcallahan.tui4j.compat.bubbletea.bubbles.stopwatch;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stopwatch bubble component.
 * Port of charmbracelet/bubbles/stopwatch.
 */
public class Stopwatch implements Model {

    private static final Duration DEFAULT_INTERVAL = Duration.ofSeconds(1);

    private static final AtomicInteger LAST_ID = new AtomicInteger(0);

    private Duration elapsed;
    private Duration interval;
    private int id;
    private int tag;
    private boolean running;

    public Stopwatch() {
        this(DEFAULT_INTERVAL);
    }

    public Stopwatch(Duration interval) {
        this.elapsed = Duration.ZERO;
        this.interval = interval;
        this.running = false;
        this.id = nextId();
    }

    public int id() {
        return id;
    }

    public Duration elapsed() {
        return elapsed;
    }

    void setElapsed(Duration elapsed) {
        this.elapsed = elapsed;
    }

    public Duration interval() {
        return interval;
    }

    public void setInterval(Duration interval) {
        this.interval = interval;
    }

    public boolean running() {
        return running;
    }

    @Override
    public Command init() {
        return start();
    }

    @Override
    public UpdateResult<Stopwatch> update(Message msg) {
        if (msg instanceof StartStopMsg startStopMsg) {
            if (startStopMsg.id() != 0 && startStopMsg.id() != id) {
                return UpdateResult.from(this);
            }
            running = startStopMsg.running();
            if (running) {
                return UpdateResult.from(this, tick());
            }
            return UpdateResult.from(this);
        }
        if (msg instanceof ResetMsg resetMsg) {
            if (resetMsg.id() != 0 && resetMsg.id() != id) {
                return UpdateResult.from(this);
            }
            elapsed = Duration.ZERO;
            return UpdateResult.from(this);
        }
        if (msg instanceof TickMsg tickMsg) {
            if (!running || (tickMsg.id() != 0 && tickMsg.id() != id)) {
                return UpdateResult.from(this);
            }
            if (tickMsg.tag() > 0 && tickMsg.tag() != tag) {
                return UpdateResult.from(this);
            }

            elapsed = elapsed.plus(interval);
            tag++;
            return UpdateResult.from(this, tick());
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        return formatDuration(elapsed);
    }

    public Command start() {
        running = true;
        return Command.sequence(
                () -> new StartStopMsg(id, true),
                tick()
        );
    }

    public Command stop() {
        running = false;
        return () -> new StartStopMsg(id, false);
    }

    public Command toggle() {
        if (running) {
            return stop();
        }
        return start();
    }

    public Command reset() {
        elapsed = Duration.ZERO;
        return () -> new ResetMsg(id);
    }

    private static int nextId() {
        return LAST_ID.incrementAndGet();
    }

    private Command tick() {
        return Command.tick(interval, __ -> new TickMsg(id, tag));
    }

    private static String formatDuration(Duration duration) {
        long nanos = duration.toNanos();
        if (nanos < 0) {
            nanos = -nanos;
        }

        long seconds = nanos / 1_000_000_000L;
        long nanosRemaining = nanos % 1_000_000_000L;

        StringBuilder sb = new StringBuilder();

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        if (hours > 0) {
            sb.append(hours).append(':');
        }

        if (minutes < 10) {
            sb.append('0');
        }
        sb.append(minutes).append(':');

        if (secs < 10) {
            sb.append('0');
        }
        sb.append(secs);

        if (nanosRemaining > 0) {
            sb.append('.');
            String nanosStr = Long.toString(nanosRemaining);
            while (nanosStr.length() < 9) {
                nanosStr = "0" + nanosStr;
            }
            int end = nanosStr.length();
            while (end > 0 && nanosStr.charAt(end - 1) == '0') {
                end--;
            }
            sb.append(nanosStr, 0, end);
        }

        return sb.toString();
    }
}
