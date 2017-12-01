package com.github.vitalibo.brickgame.game.tanks;

import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.game.Shape;
import com.github.vitalibo.brickgame.util.Random;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class Battlefield {

    private static final Shape TANK_SHADOW = Shape.of(3, 0x7, 0x7, 0x7);

    @Getter
    private final List<Tank> tanks = new ArrayList<>();

    public int canDoUp(Tank tank) {
        return testDoStep(tank, Tank::doUp, Direction.DOWN);
    }

    public int canDoRight(Tank tank) {
        return testDoStep(tank, Tank::doRight, Direction.LEFT);
    }

    public int canDoDown(Tank tank) {
        return testDoStep(tank, Tank::doDown, Direction.UP);
    }

    public int canDoLeft(Tank tank) {
        return testDoStep(tank, Tank::doLeft, Direction.RIGHT);
    }

    private int testDoStep(Tank tank, Consumer<Tank> step, Direction direction) {
        Tank clone = new Tank(tank);
        step.accept(clone);

        if (clone.verify()) {
            return 0;
        }

        if (!hasOverlap(clone)) {
            return 1;
        }

        if (tank.getDirection() != direction) {
            return 0;
        }

        step.accept(clone);
        if (clone.verify() || hasOverlap(clone)) {
            return 0;
        }

        return 2;
    }


    private boolean hasOverlap(Tank tank) {
        return tanks.stream()
            .filter(o -> !o.equals(tank))
            .flatMap(Tank::stream)
            .anyMatch(tank::contains);
    }

    public void spawn() {
        spawn(4);
    }

    private void spawn(int attempt) {
        if (attempt == 0) {
            return;
        }

        Tank tank = Spawn.randomEnemyTanks();
        boolean hasOverlap = tanks.stream()
            .filter(o -> !o.equals(tank))
            .flatMap(o -> TANK_SHADOW.apply(o.getPoint()))
            .anyMatch(tank::contains);

        if (hasOverlap) {
            spawn(attempt - 1);
            return;
        }

        tanks.add(tank);
    }

    static class Spawn {

        private static final List<Point> SPAWN_POINTS =
            Arrays.asList(
                Point.of(0, 0), Point.of(0, 7),
                Point.of(17, 0), Point.of(17, 7));

        public static Tank myTank() {
            return point(Point.of(9, 4));
        }

        public static List<Tank> enemyTanks() {
            return SPAWN_POINTS.stream().map(Battlefield.Spawn::point)
                .collect(Collectors.toList());
        }

        private static Tank randomEnemyTanks() {
            return point(SPAWN_POINTS.get(
                Random.nextInt(SPAWN_POINTS.size())));
        }

        private static Tank point(Point point) {
            return new Tank(Point.of(point), Direction.random());
        }

    }

}