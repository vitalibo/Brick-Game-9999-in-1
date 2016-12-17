package com.github.vitalibo.brickgame.core;

public interface Number {

    int get();

    void set(int value);

    default int inc() {
        return inc(1);
    }

    default int inc(int h) {
        return Builder.of(get())
            .map(v -> v + h)
            .with(this::set)
            .get();
    }

    default int dec() {
        return dec(1);
    }

    default int dec(int h) {
        return Builder.of(get())
            .map(v -> v - h)
            .with(this::set)
            .get();
    }

}
