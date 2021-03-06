package com.github.vitalibo.brickgame.game.tetris.tetromino;

import com.github.vitalibo.brickgame.game.Shape;
import com.github.vitalibo.brickgame.game.tetris.Tetromino;

class L extends Tetromino {

    private static final Shape[] STATES;

    static {
        STATES = new Shape[]{
            Shape.of(4, 0x0, 0x7, 0x4, 0x0),
            Shape.of(4, 0x2, 0x2, 0x3, 0x0),
            Shape.of(4, 0x1, 0x7, 0x0, 0x0),
            Shape.of(4, 0x6, 0x2, 0x2, 0x0)};
    }

    L(int state) {
        super(STATES, state);
    }

}