package com.github.vitalibo.brickgame.game.tanks;

import com.github.vitalibo.brickgame.game.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
class Shot implements Runnable {

    @Getter
    private final Point point;
    @Getter
    private final Direction direction;

    @Override
    public void run() {
        direction.accept(point);
    }

    public boolean verify() {
        return (point.getX() < 0 || point.getX() > 9)
            || (point.getY() < 0 || point.getY() > 19);
    }

}
