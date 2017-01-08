package com.github.vitalibo.brickgame.game.tetris;

import com.github.vitalibo.brickgame.core.Context;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.game.tetris.tetromino.Factory;
import com.github.vitalibo.brickgame.util.CanvasTranslator;
import lombok.Getter;
import lombok.Synchronized;

import java.util.List;
import java.util.function.Consumer;

public class TetrisGame extends Game {

    @Getter
    private Board board;
    @Getter
    private Tetromino tetromino;
    @Getter
    private Tetromino next;

    public TetrisGame(Context context) {
        super(context);

        score.set(0);
    }

    @Override
    public void init() {
        board = Board.init(level.get());
        tetromino = Factory.generate();
        next = Factory.generate();
        repaint(next);

        kernel.job(this, 700 - speed.get() * 40, job -> doDown());
    }

    @Override
    public void doDown() {
        if (board.isFull()) {
            // TODO: change screen clean instead of crash
            crash(Point.of(-5, -5));
            return;
        }

        Tetromino tetromino = Tetromino.from(this.tetromino);
        tetromino.doDown();

        if (this.tetromino.inBottom() || !board.verify(tetromino)) {
            add(this.tetromino);
            return;
        }

        this.tetromino = tetromino;
        repaint();
    }

    private void add(Tetromino tetromino) {
        board.add(tetromino);

        int line = board.cleanUp();
        if (line != 0) {
            score.inc(line * 100);
        }

        this.tetromino = next;
        next = Factory.generate();
        repaint(next);
        repaint();
    }

    @Override
    public void doLeft() {
        doStep(Tetromino::doLeft);
    }

    @Override
    public void doRight() {
        doStep(Tetromino::doRight);
    }

    @Override
    public void doUp() {
        doRotate();
    }

    @Override
    public void doRotate() {
        doStep(Tetromino::doRotate);
    }

    @Synchronized
    private void doStep(Consumer<Tetromino> step) {
        Tetromino tetromino = Tetromino.from(this.tetromino);
        step.accept(tetromino);

        if (!board.verify(tetromino)) {
            return;
        }

        this.tetromino = tetromino;
        repaint();
    }

    private void repaint() {
        boolean[][] canvas = CanvasTranslator.from(tetromino.stream(), board.stream());

        super.board.draw(canvas);
    }

    private void repaint(List<Point> points) {
        boolean[][] canvas = new boolean[4][4];
        points.forEach(point -> canvas[point.getY() + 2][point.getX() - 3] = true);
        preview.draw(canvas);
    }

}