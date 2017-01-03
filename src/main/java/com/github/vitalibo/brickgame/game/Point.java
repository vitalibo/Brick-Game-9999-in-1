package com.github.vitalibo.brickgame.game;

import com.github.vitalibo.brickgame.core.Controllable;
import com.github.vitalibo.brickgame.core.GameException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public final class Point implements Controllable {

    private int y;
    private int x;

    private Point(Point point) {
        this.y = point.y;
        this.x = point.x;
    }

    public void doMove(int hy, int hx) {
        this.y += hy;
        this.x += hx;
    }

    @Override
    public void doDown() {
        doMove(1, 0);
    }

    @Override
    public void doLeft() {
        doMove(0, -1);
    }

    @Override
    public void doRight() {
        doMove(0, 1);
    }

    @Override
    public void doUp() {
        doMove(-1, 0);
    }

    @Override
    public void doRotate() {
        throw new UnsupportedOperationException();
    }

    public void verify() {
        if (x < 0 || x >= 10) {
            throw new GameException(this, "The X value must be in the range 0 - 9.");
        }

        if (y < 0 || y >= 20) {
            throw new GameException(this, "The Y value must be in the range 0 - 19.");
        }
    }

    public static Point of(Point point) {
        return new Point(point);
    }

}