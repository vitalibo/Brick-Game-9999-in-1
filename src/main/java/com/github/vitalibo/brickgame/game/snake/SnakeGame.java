package com.github.vitalibo.brickgame.game.snake;

import com.github.vitalibo.brickgame.core.Context;
import com.github.vitalibo.brickgame.core.GameException;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.util.CanvasTranslator;
import lombok.Getter;

import java.util.function.Consumer;

public class SnakeGame extends Game {

    private final Step DOWN = new Step(Direction.DOWN, Point::doDown);
    private final Step LEFT = new Step(Direction.LEFT, Point::doLeft);
    private final Step RIGHT = new Step(Direction.RIGHT, Point::doRight);
    private final Step UP = new Step(Direction.UP, Point::doUp);

    @Getter
    private Map map;
    @Getter
    private Snake snake;
    @Getter
    private Mouse mouse;
    @Getter
    private Direction direction;
    @Getter
    private boolean keyPressed;

    public SnakeGame(Context context) {
        super(context);
        setUp();
    }

    private void setUp() {
        life.set(3);
        score.set(0);
    }

    @Override
    public void init() {
        map = Map.of(level.get());
        snake = Snake.init();
        mouse = Mouse.Generator.init(snake, map);
        direction = Direction.NOT_DEFINED;

        kernel.job(this, 300 - speed.get() * 15, job -> onMove());
        repaint();
    }

    private void onMove() {
        if (direction == Direction.NOT_DEFINED) {
            return;
        }

        if (keyPressed) {
            keyPressed = false;
            return;
        }

        switch (direction) {
            case DOWN:
                DOWN.doStep();
                break;
            case LEFT:
                LEFT.doStep();
                break;
            case RIGHT:
                RIGHT.doStep();
                break;
            case UP:
                UP.doStep();
                break;
        }
    }

    @Override
    public void doDown() {
        if (!DOWN.doStep()) {
            return;
        }

        keyPressed = true;
    }

    @Override
    public void doLeft() {
        if (direction == Direction.NOT_DEFINED) {
            return;
        }

        if (!LEFT.doStep()) {
            return;
        }

        keyPressed = true;
    }

    @Override
    public void doRight() {
        if (!RIGHT.doStep()) {
            return;
        }

        keyPressed = true;
    }

    @Override
    public void doUp() {
        if (direction == Direction.NOT_DEFINED) {
            return;
        }

        if (!UP.doStep()) {
            return;
        }

        keyPressed = true;
    }

    @Override
    public void doRotate() {
        onMove();
    }

    private void verifyLevelUp() {
        if (snake.size() <= 33) {
            return;
        }

        kernel.forEach((s, job) -> job.kill());
        if (speed.inc() == 0) {
            level.inc();
        }

        init();
    }

    private void repaint() {
        boolean[][] from = CanvasTranslator.from(map.stream(), snake.stream(), mouse.stream());

        board.draw(from);
    }

    public class Step {

        private final Direction course;
        private final Consumer<Point> action;

        public Step(Direction course, Consumer<Point> action) {
            this.course = course;
            this.action = action;
        }

        public boolean doStep() {
            if (!verifyDirection(course)) {
                return false;
            }

            try {
                Point head = snake.getHead();
                action.accept(head);
                head.verify();

                if (mouse.verifyEatMouse(head)) {
                    score.inc(10);
                } else {
                    snake.remove(0);
                }

                snake.verifyEatSelf(head);
                map.verifyCrashOnBorder(head);

                repaint();
                verifyLevelUp();
            } catch (GameException ex) {
                crash(ex.getPoint());
            }

            return true;
        }

        private boolean verifyDirection(Direction course) {
            if (direction == course.reverse()) {
                return false;
            }

            direction = course;
            return true;
        }

    }

}