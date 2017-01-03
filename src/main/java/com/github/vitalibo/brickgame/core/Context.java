package com.github.vitalibo.brickgame.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Context {

    @Getter
    private final Canvas board;
    @Getter
    private final Canvas preview;
    @Getter
    private final Number score;
    @Getter
    private final Number speed;
    @Getter
    private final Number level;
    @Getter
    private final Number life;
    @Getter
    private final State sound;
    @Getter
    private final State pause;
    @Getter
    private final Kernel kernel;
    @Getter
    private final Controller controller;

}