package com.github.vitalibo.brickgame.game.tetris.tetromino;

import com.github.vitalibo.brickgame.game.Shape;
import com.github.vitalibo.brickgame.game.tetris.Tetromino;

class O extends Tetromino {

    private static final Shape[] STATES;

    static {
        STATES = new Shape[]{
            Shape.of(4, 0x0, 0x6, 0x6, 0x0),
            Shape.of(4, 0x0, 0x6, 0x6, 0x0),
            Shape.of(4, 0x0, 0x6, 0x6, 0x0),
            Shape.of(4, 0x0, 0x6, 0x6, 0x0)};
    }

    O(int state) {
        super(STATES, state);
    }

}