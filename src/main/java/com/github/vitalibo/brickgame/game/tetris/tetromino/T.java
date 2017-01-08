package com.github.vitalibo.brickgame.game.tetris.tetromino;

import com.github.vitalibo.brickgame.game.Shape;
import com.github.vitalibo.brickgame.game.tetris.Tetromino;

class T extends Tetromino {

    private static final Shape[] STATES;

    static {
        STATES = new Shape[]{
            Shape.of(4, 0x0, 0x7, 0x2, 0x0),
            Shape.of(4, 0x2, 0x3, 0x2, 0x0),
            Shape.of(4, 0x2, 0x7, 0x0, 0x0),
            Shape.of(4, 0x2, 0x6, 0x2, 0x0)};
    }

    T(int state) {
        super(STATES, state);
    }

}