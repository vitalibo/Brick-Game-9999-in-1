package com.github.vitalibo.brickgame.core;

public interface State {

    default boolean change() {
        return Builder.of(get())
            .map(s -> !s)
            .with(this::set)
            .get();
    }

    boolean get();

    void set(boolean state);

}