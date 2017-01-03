package com.github.vitalibo.brickgame.core;

import com.github.vitalibo.brickgame.game.Point;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class GameException extends RuntimeException {

    @Getter
    private final Point point;

    public GameException(Point point) {
        super();
        this.point = point;
    }

    public GameException(Point point, String message) {
        super(message);
        this.point = point;
    }

    public GameException(Point point, String message, Throwable cause) {
        super(message, cause);
        this.point = point;
    }

    public GameException(Point point, Throwable cause) {
        super(cause);
        this.point = point;
    }

}