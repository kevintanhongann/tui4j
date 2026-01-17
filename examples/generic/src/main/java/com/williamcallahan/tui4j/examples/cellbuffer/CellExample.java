package com.williamcallahan.tui4j.examples.cellbuffer;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.harmonica.Spring;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;

import java.time.Duration;

public class CellExample implements Model {

    private static final double FPS = 60.0;
    private static final double FREQUENCY = 7.5;
    private static final double DAMPING = 0.15;
    private static final String ASTERISK = "*";

    private CellBuffer cells;
    private Spring spring;
    private double targetX;
    private double targetY;
    private double x;
    private double y;
    private double xVelocity;
    private double yVelocity;

    public CellExample() {
        this.cells = new CellBuffer();
        this.spring = Spring.newSpring(1.0 / FPS, FREQUENCY, DAMPING);
        this.targetX = 0;
        this.targetY = 0;
        this.x = 0;
        this.y = 0;
        this.xVelocity = 0;
        this.yVelocity = 0;
    }

    private static void drawEllipse(CellBuffer cb, double xc, double yc, double rx, double ry) {
        double dx, dy, d1, d2;
        double x = 0;
        double y = ry;

        d1 = ry * ry - rx * rx * ry + 0.25 * rx * rx;
        dx = 2 * ry * ry * x;
        dy = 2 * rx * rx * y;

        while (dx < dy) {
            cb.set((int) (x + xc), (int) (y + yc));
            cb.set((int) (-x + xc), (int) (y + yc));
            cb.set((int) (x + xc), (int) (-y + yc));
            cb.set((int) (-x + xc), (int) (-y + yc));
            if (d1 < 0) {
                x++;
                dx = dx + (2 * ry * ry);
                d1 = d1 + dx + (ry * ry);
            } else {
                x++;
                y--;
                dx = dx + (2 * ry * ry);
                dy = dy - (2 * rx * rx);
                d1 = d1 + dx - dy + (ry * ry);
            }
        }

        d2 = ((ry * ry) * ((x + 0.5) * (x + 0.5))) + ((rx * rx) * ((y - 1) * (y - 1))) - (rx * rx * ry * ry);

        while (y >= 0) {
            cb.set((int) (x + xc), (int) (y + yc));
            cb.set((int) (-x + xc), (int) (y + yc));
            cb.set((int) (x + xc), (int) (-y + yc));
            cb.set((int) (-x + xc), (int) (-y + yc));
            if (d2 > 0) {
                y--;
                dy = dy - (2 * rx * rx);
                d2 = d2 + (rx * rx) - dy;
            } else {
                y--;
                x++;
                dx = dx + (2 * ry * ry);
                dy = dy - (2 * rx * rx);
                d2 = d2 + dx - dy + (rx * rx);
            }
        }
    }

    @Override
    public Command init() {
        return Command.tick(Duration.ofNanos((long) (1_000_000_000.0 / FPS)), time -> new FrameMsg());
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            String key = keyPressMessage.key();
            if ("q".equals(key) || "Q".equals(key) || "ctrl+c".equals(key) || "esc".equals(key)) {
                return new UpdateResult<>(this, QuitMessage::new);
            }
        }

        if (msg instanceof WindowSizeMsg windowSizeMsg) {
            if (!cells.ready()) {
                targetX = windowSizeMsg.width() / 2.0;
                targetY = windowSizeMsg.height() / 2.0;
            }
            cells.init(windowSizeMsg.width(), windowSizeMsg.height());
            return UpdateResult.from(this);
        }

        if (msg instanceof MouseMsg mouseMsg) {
            if (!cells.ready()) {
                return UpdateResult.from(this);
            }
            targetX = mouseMsg.x();
            targetY = mouseMsg.y();
            return UpdateResult.from(this);
        }

        if (msg instanceof FrameMsg) {
            if (!cells.ready()) {
                return UpdateResult.from(this, Command.tick(Duration.ofNanos((long) (1_000_000_000.0 / FPS)), time -> new FrameMsg()));
            }

            cells.wipe();
            double[] xResult = spring.update(x, xVelocity, targetX);
            x = xResult[0];
            xVelocity = xResult[1];
            double[] yResult = spring.update(y, yVelocity, targetY);
            y = yResult[0];
            yVelocity = yResult[1];
            drawEllipse(cells, x, y, 16, 8);
            return UpdateResult.from(this, Command.tick(Duration.ofNanos((long) (1_000_000_000.0 / FPS)), time -> new FrameMsg()));
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        return cells.toString();
    }

    public static void main(String[] args) {
        CellExample model = new CellExample();
        new Program(model)
                .withAltScreen()
                .withMouseCellMotion()
                .run();
    }

    private static class CellBuffer {
        private String[] cells;
        private int stride;

        public void init(int w, int h) {
            if (w == 0) {
                return;
            }
            stride = w;
            cells = new String[w * h];
            wipe();
        }

        public void set(int x, int y) {
            int i = y * stride + x;
            if (i > cells.length - 1 || x < 0 || y < 0 || x >= width() || y >= height()) {
                return;
            }
            cells[i] = ASTERISK;
        }

        public void wipe() {
            for (int i = 0; i < cells.length; i++) {
                cells[i] = " ";
            }
        }

        public int width() {
            return stride;
        }

        public int height() {
            int h = cells.length / stride;
            if (cells.length % stride != 0) {
                h++;
            }
            return h;
        }

        public boolean ready() {
            return cells != null && cells.length > 0;
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < cells.length; i++) {
                if (i > 0 && i % stride == 0 && i < cells.length - 1) {
                    b.append('\n');
                }
                b.append(cells[i]);
            }
            return b.toString();
        }
    }

    private static class FrameMsg implements Message {
    }

    private static class WindowSizeMsg implements Message {
        private final int width;
        private final int height;

        public WindowSizeMsg(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int width() {
            return width;
        }

        public int height() {
            return height;
        }
    }

    private static class MouseMsg implements Message {
        private final int x;
        private final int y;

        public MouseMsg(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }
    }
}
