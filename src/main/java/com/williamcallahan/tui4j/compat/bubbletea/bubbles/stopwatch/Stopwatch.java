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

    // Duration constants in nanoseconds for formatDuration()
    private static final long NANOS_PER_HOUR = 3_600_000_000_000L;
    private static final long NANOS_PER_MINUTE = 60_000_000_000L;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_MILLI = 1_000_000L;
    private static final long NANOS_PER_MICRO = 1_000L;

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

    /**
     * Renders the elapsed time.
     * <p>
     * Formats the duration similar to Go's time.Duration string representation (e.g., "1h2m3s").
     */
    @Override
    public String view() {
        return formatDuration(elapsed);
    }

    /**
     * Returns a command to resume the stopwatch tick loop.
     */
    public Command start() {
        return Command.sequence(
                () -> new StartStopMsg(id, true),
                tick()
        );
    }

    /**
     * Returns a command to halt the tick loop.
     */
    public Command stop() {
        return () -> new StartStopMsg(id, false);
    }

    /**
     * Returns a command to reset the elapsed time to zero.
     */
    public Command reset() {
        return () -> new ResetMsg(id);
    }

    /**
     * Returns a command to switch between running and stopped states.
     */
    public Command toggle() {
        return Command.sequence(
                () -> new StartStopMsg(id, !running),
                tick()
        );
    }

    private static int nextId() {
        return LAST_ID.incrementAndGet();
    }

    private Command tick() {
        return Command.tick(interval, __ -> new TickMsg(id, tag));
    }

    /** Formats duration in Go's time.Duration.String() style: "1h30m45s", "500ms", "1µs" */
    private static String formatDuration(Duration duration) {
        if (duration.isZero()) {
            return "0s";
        }

        long nanos = duration.toNanos();
        StringBuilder sb = new StringBuilder();

        if (nanos < 0) {
            sb.append("-");
            nanos = -nanos;
        }

        long hours = nanos / NANOS_PER_HOUR;
        if (hours > 0) {
            sb.append(hours).append("h");
            nanos %= NANOS_PER_HOUR;
        }

        long minutes = nanos / NANOS_PER_MINUTE;
        if (minutes > 0) {
            sb.append(minutes).append("m");
            nanos %= NANOS_PER_MINUTE;
        }

        if (hours == 0 && minutes == 0 && nanos < NANOS_PER_SECOND && nanos > 0) {
            if (nanos >= NANOS_PER_MILLI) {
                double ms = nanos / (double) NANOS_PER_MILLI;
                if (ms == (long) ms) {
                    sb.append((long) ms).append("ms");
                } else {
                    sb.append(String.format("%.3gms", ms).replaceAll("\\.?0+ms$", "ms"));
                }
            } else if (nanos >= NANOS_PER_MICRO) {
                double us = nanos / (double) NANOS_PER_MICRO;
                if (us == (long) us) {
                    sb.append((long) us).append("µs");
                } else {
                    sb.append(String.format("%.3gµs", us).replaceAll("\\.?0+µs$", "µs"));
                }
            } else {
                sb.append(nanos).append("ns");
            }
            return sb.toString();
        }

        double seconds = nanos / (double) NANOS_PER_SECOND;
        if (seconds > 0 || sb.isEmpty()) {
            if (seconds == (long) seconds) {
                sb.append((long) seconds).append("s");
            } else {
                String formatted = String.format("%.9f", seconds).replaceAll("0+$", "").replaceAll("\\.$", "");
                sb.append(formatted).append("s");
            }
        }

        return sb.toString();
    }
}
