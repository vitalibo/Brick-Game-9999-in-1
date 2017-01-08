package com.github.vitalibo.brickgame.game.tetris.tetromino;

import com.github.vitalibo.brickgame.game.tetris.Tetromino;

import java.util.Random;

public class Factory {

    private static final Random random = new Random();

    public static Tetromino generateI() {
        return createI(random.nextInt(4));
    }

    public static Tetromino createI(int state) {
        return new I(state);
    }

    public static Tetromino generateJ() {
        return createJ(random.nextInt(4));
    }

    public static Tetromino createJ(int state) {
        return new J(state);
    }

    public static Tetromino generateL() {
        return createL(random.nextInt(4));
    }

    public static Tetromino createL(int state) {
        return new L(state);
    }

    public static Tetromino generateO() {
        return createO(random.nextInt(4));
    }

    public static Tetromino createO(int state) {
        return new O(state);
    }

    public static Tetromino generateS() {
        return createS(random.nextInt(4));
    }

    public static Tetromino createS(int state) {
        return new S(state);
    }

    public static Tetromino generateT() {
        return createT(random.nextInt(4));
    }

    public static Tetromino createT(int state) {
        return new T(state);
    }

    public static Tetromino generateZ() {
        return createZ(random.nextInt(4));
    }

    public static Tetromino createZ(int state) {
        return new Z(state);
    }

    public static Tetromino generate() {
        switch (random.nextInt(7)) {
            case 0:
                return generateI();
            case 1:
                return generateJ();
            case 2:
                return generateL();
            case 3:
                return generateO();
            case 4:
                return generateS();
            case 5:
                return generateT();
            case 6:
                return generateZ();
            default:
                throw new IllegalStateException();
        }
    }
}