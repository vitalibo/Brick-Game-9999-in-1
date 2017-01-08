package com.github.vitalibo.brickgame.game.tetris.tetromino;

import com.github.vitalibo.brickgame.game.Shape;
import com.github.vitalibo.brickgame.game.tetris.Tetromino;

class S extends Tetromino {

    private static final Shape[] STATES;

    static {
        STATES = new Shape[]{
            Shape.of(4, 0x0, 0x3, 0x6, 0x0),
            Shape.of(4, 0x2, 0x3, 0x1, 0x0),
            Shape.of(4, 0x0, 0x3, 0x6, 0x0),
            Shape.of(4, 0x2, 0x3, 0x1, 0x0)};
    }

    S(int state) {
        super(STATES, state);
    }

}