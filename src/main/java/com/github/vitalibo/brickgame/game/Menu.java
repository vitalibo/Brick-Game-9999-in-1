package com.github.vitalibo.brickgame.game;

import com.github.vitalibo.brickgame.core.Context;
import com.github.vitalibo.brickgame.util.CanvasTranslator;
import lombok.Setter;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Menu extends Game {

    private static final Shape[] DIGITS;
    private static final Point[] POSITIONS;

    @Setter
    private static Class<? extends Game>[] games;

    static {
        DIGITS = new Shape[]{
            Shape.of(new int[][]{{0, 1, 1, 0}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 0, 0, 1}, {0, 1, 1, 0}}),    // 0
            Shape.of(new int[][]{{0, 0, 1, 0}, {0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 1, 1, 1}}),    // 1
            Shape.of(new int[][]{{0, 1, 1, 1}, {0, 0, 0, 1}, {0, 1, 1, 1}, {0, 1, 0, 0}, {0, 1, 1, 1}}),    // 2
            Shape.of(new int[][]{{0, 1, 1, 1}, {0, 0, 0, 1}, {0, 1, 1, 1}, {0, 0, 0, 1}, {0, 1, 1, 1}}),    // 3
            Shape.of(new int[][]{{0, 1, 0, 1}, {0, 1, 0, 1}, {0, 1, 1, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}}),    // 4
            Shape.of(new int[][]{{0, 1, 1, 1}, {0, 1, 0, 0}, {0, 1, 1, 1}, {0, 0, 0, 1}, {0, 1, 1, 1}}),    // 5
            Shape.of(new int[][]{{0, 1, 1, 1}, {0, 1, 0, 0}, {0, 1, 1, 1}, {0, 1, 0, 1}, {0, 1, 1, 1}}),    // 6
            Shape.of(new int[][]{{0, 1, 1, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}}),    // 7
            Shape.of(new int[][]{{0, 1, 1, 1}, {0, 1, 0, 1}, {0, 1, 1, 1}, {0, 1, 0, 1}, {0, 1, 1, 1}}),    // 8
            Shape.of(new int[][]{{0, 1, 1, 1}, {0, 1, 0, 1}, {0, 1, 1, 1}, {0, 0, 0, 1}, {0, 1, 1, 1}})};   // 9

        POSITIONS = new Point[]{Point.of(14, 5), Point.of(10, 1), Point.of(5, 5), Point.of(1, 1)};
    }

    private static int value;

    public Menu(Context context) {
        super(context);
    }

    @Override
    public void init() {
        repaint();
    }

    @Override
    public void doDown() {
        level.inc();
    }

    @Override
    public void doLeft() {
        if (--value < 0) {
            value = 9999;
        }

        repaint();
    }

    @Override
    public void doRight() {
        if (++value > 9999) {
            value = 0;
        }

        repaint();
    }

    @Override
    public void doUp() {
        speed.inc();
    }

    @Override
    public void doRotate() {
        controller.init(games[value % games.length]);
    }

    private void repaint() {
        int[] values = IntStream.range(0, 4)
            .map(i -> (value / (int) Math.pow(10, i)) % 10)
            .toArray();

        Stream<Point> points = IntStream.range(0, 4)
            .mapToObj(i -> DIGITS[values[i]].apply(POSITIONS[i]))
            .flatMap(stream -> stream);

        board.draw(CanvasTranslator.from(points));
    }

}