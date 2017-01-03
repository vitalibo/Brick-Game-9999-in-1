package com.github.vitalibo.brickgame.core.ui;

import com.github.vitalibo.brickgame.core.Canvas;
import com.github.vitalibo.brickgame.core.State;
import com.github.vitalibo.brickgame.util.BooleanCollector;
import com.github.vitalibo.brickgame.util.Builder;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class BrickPanel extends JPanel implements Canvas {

    private static final Color ON = new Color(0x000000);
    private static final Color OFF = new Color(0x61705B);

    private final Brick[][] bricks;

    private final int width;
    private final int height;

    BrickPanel(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.bricks = IntStream.range(0, height)
            .mapToObj(h -> IntStream.range(0, width)
                .mapToObj(w -> new Brick())
                .peek(this::add)
                .toArray(Brick[]::new))
            .toArray(v -> new Brick[height][width]);
        this.setLayout(Builder.of(new GridLayout(0, width))
            .with(l -> l.setHgap(1))
            .with(l -> l.setVgap(1))
            .get());
        this.setOpaque(false);
    }

    @Override
    public synchronized void draw(boolean[][] src) {
        IntStream.range(0, height)
            .forEach(h -> IntStream.range(0, width)
                .forEach(w -> bricks[h][w].set(src[h][w])));
    }

    @Override
    public synchronized boolean[][] get() {
        return IntStream.range(0, height)
            .mapToObj(h -> IntStream.range(0, width)
                .mapToObj(w -> bricks[h][w].get())
                .collect(BooleanCollector.toArray()))
            .collect(BooleanCollector.toTwoDimensionalArray());
    }

    static class Brick extends JPanel implements State {

        private boolean state;

        Brick() {
            this.setOpaque(false);
        }

        @Override
        public boolean get() {
            return state;
        }

        @Override
        public void set(boolean state) {
            if (this.state == state) {
                return;
            }

            this.state = state;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Color color = state ? ON : OFF;
            g.setColor(color);
            g.drawRect(0, 0, 9, 9);
            g.setColor(color);
            g.fillRect(3, 3, 4, 4);
        }

    }

}