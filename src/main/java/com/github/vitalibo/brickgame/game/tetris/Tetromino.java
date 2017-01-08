package com.github.vitalibo.brickgame.game.tetris;

import com.github.vitalibo.brickgame.core.Controllable;
import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.game.Shape;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Tetromino extends ArrayList<Point> implements Controllable {

    @Getter
    private final Shape[] states;
    @Getter
    private final Point dot;
    @Getter
    private int state;

    public Tetromino(Shape[] states, int state) {
        this.states = states;
        this.state = state;
        this.dot = Point.of(-2, 3);
        states[state % 4].apply(dot)
            .forEach(this::add);
    }

    private Tetromino(Tetromino that) {
        this.states = that.states;
        this.state = that.state;
        this.dot = Point.of(that.dot);
        that.forEach(p -> this.add(Point.of(p)));
    }

    @Override
    public void doDown() {
        if (!verifyStep(Point::doDown)) {
            return;
        }

        dot.doDown();
        this.forEach(Point::doDown);
    }

    @Override
    public void doLeft() {
        if (!verifyStep(Point::doLeft)) {
            return;
        }

        dot.doLeft();
        this.forEach(Point::doLeft);
    }

    @Override
    public void doRight() {
        if (!verifyStep(Point::doRight)) {
            return;
        }

        dot.doRight();
        this.forEach(Point::doRight);
    }

    @Override
    public void doUp() {
        throw new UnsupportedOperationException("doUp");
    }

    @Override
    public void doRotate() {
        List<Point> figure = rotate();
        if (!verifyRotate(figure)) {
            return;
        }

        state++;
        clear();
        figure.forEach(this::add);
    }

    private boolean verifyStep(Consumer<Point> step) {
        return stream().map(Point::of)
            .peek(step)
            .noneMatch(this::verify);
    }

    private boolean verifyRotate(List<Point> points) {
        return points.stream().noneMatch(this::verify);
    }

    private boolean verify(Point point) {
        return point.getX() < 0 || point.getX() >= 10 || point.getY() >= 20;
    }

    private List<Point> rotate() {
        final int nextSate = this.state + 1;
        return states[nextSate % 4]
            .apply(centralization(Point.of(dot), nextSate))
            .collect(Collectors.toList());
    }

    private Point centralization(Point point, int state) {
        boolean match = states[state % 4]
            .apply(point)
            .map(Point::getX)
            .anyMatch(x -> x < 0 || x >= 10);

        if (!match) {
            return point;
        }

        if (point.getX() < 5) {
            point.doRight();
        } else {
            point.doLeft();
        }

        return centralization(point, state);
    }

    public boolean inBottom() {
        return stream()
            .map(Point::getY)
            .anyMatch(y -> y == 19);
    }

    public static Tetromino from(Tetromino that) {
        return new Tetromino(that);
    }

}