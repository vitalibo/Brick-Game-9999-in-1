package com.github.vitalibo.brickgame.core.ui;

import com.github.vitalibo.brickgame.core.Number;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.stream.IntStream;

public class NumberPanel extends JPanel implements Number {

    private static final Image[] NUMERICAL_DIGIT = IntStream.range(0, 10)
        .mapToObj(i -> String.format("ui/digit/%d.bmp", i))
        .map(NumberPanel::resourceAsImage)
        .toArray(Image[]::new);

    private final int maxValue;

    private Digit[] sequence;
    private int value;

    NumberPanel(int capacity) {
        this(capacity, Integer.MAX_VALUE);
    }

    NumberPanel(int capacity, final int maxValue) {
        this.maxValue = maxValue;
        this.value = 0;
        this.sequence = IntStream.range(0, capacity)
            .mapToObj(i -> new Digit())
            .peek(this::add)
            .toArray(Digit[]::new);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
        this.setOpaque(false);
        this.repaint();
    }

    @Override
    public int get() {
        return value;
    }

    @Override
    public synchronized void set(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("The value must be mere 0.");
        }

        if (value > maxValue) {
            this.value = 0;
        } else {
            this.value = value;
        }

        IntStream.range(0, sequence.length)
            .mapToObj(i -> new AbstractMap.SimpleEntry<>(i, 0))
            .peek(entry -> entry.setValue((this.value / (int) Math.pow(10, sequence.length - entry.getKey() - 1)) % 10))
            .forEach(entry -> sequence[entry.getKey()].set(entry.getValue()));
    }

    @SneakyThrows(IOException.class)
    private static Image resourceAsImage(String resource) {
        return ImageIO.read(ClassLoader.getSystemResourceAsStream(resource));
    }

    static class Digit extends JPanel implements Number {

        private int value;

        Digit() {
            this.setPreferredSize(new Dimension(8, 13));
        }

        public int get() {
            return value;
        }

        public void set(int value) {
            if (value < 0 || value > 9) {
                throw new IllegalArgumentException("The value must be in the range 0 - 9.");
            }

            if (this.value == value) {
                return;
            }

            this.value = value;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(NUMERICAL_DIGIT[value], 0, 0, null);
        }

    }

}