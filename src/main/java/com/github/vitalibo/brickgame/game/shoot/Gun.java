package com.github.vitalibo.brickgame.game.shoot;

import com.github.vitalibo.brickgame.game.Point;
import lombok.Getter;

class Gun {

    @Getter
    private final Point point;

    Gun() {
        point = Point.of(19, 4);
    }

    public void doLeft() {
        if (point.getX() < 1) {
            return;
        }

        point.doLeft();
    }

    public void doRight() {
        if (point.getX() > 8) {
            return;
        }

        point.doRight();
    }

    Point fire() {
        return Point.of(point);
    }

}
