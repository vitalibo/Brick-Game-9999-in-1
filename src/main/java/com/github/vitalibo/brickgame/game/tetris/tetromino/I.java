package com.github.vitalibo.brickgame.game.tetris.tetromino;

import com.github.vitalibo.brickgame.game.Shape;
import com.github.vitalibo.brickgame.game.tetris.Tetromino;

class I extends Tetromino {

    private static final Shape[] STATES;

    static {
        STATES = new Shape[]{
            Shape.of(4, 0x0, 0xF, 0x0, 0x0),
            Shape.of(4, 0x2, 0x2, 0x2, 0x2),
            Shape.of(4, 0x0, 0xF, 0x0, 0x0),
            Shape.of(4, 0x2, 0x2, 0x2, 0x2)};
    }

    I(int state) {
        super(STATES, state);
    }

}