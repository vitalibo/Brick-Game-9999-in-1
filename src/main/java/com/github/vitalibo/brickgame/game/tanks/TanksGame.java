package com.github.vitalibo.brickgame.game.tanks;

import com.github.vitalibo.brickgame.core.Context;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.util.CanvasTranslator;
import com.github.vitalibo.brickgame.util.Random;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TanksGame extends Game {

    private final Battlefield battlefield = new Battlefield();
    private final ArtificialIntelligence ai = new ArtificialIntelligence();
    private final Physics physics = new Physics();

    private List<Shot> myShots = new CopyOnWriteArrayList<>();
    private List<Shot> shots = new CopyOnWriteArrayList<>();

    private Tank myTank;
    private List<Tank> tanks;

    public TanksGame(Context context) {
        super(context);
    }

    @Override
    public void init() {
        score.set(0);
        myTank = Battlefield.Spawn.myTank();

        tanks = battlefield.getTanks();
        tanks.addAll(Battlefield.Spawn.enemyTanks());
        tanks.add(myTank);

        kernel.job(this, 1000 - speed.get() * 45, job -> physics.tanksMove());
        kernel.job("shots", 150, job -> physics.bulletFlight());
    }

    @Override
    public void doDown() {
        if (doDown(myTank)) repaint();
    }

    @Override
    public void doLeft() {
        if (doLeft(myTank)) repaint();
    }

    @Override
    public void doRight() {
        if (doRight(myTank)) repaint();
    }

    @Override
    public void doUp() {
        if (doUp(myTank)) repaint();
    }

    @Override
    public void doRotate() {
        myShots.add(myTank.doShot());
        repaint();
    }

    private boolean doDown(Tank tank) {
        return doStep(tank, Battlefield::canDoDown, Tank::doDown);
    }

    private boolean doLeft(Tank tank) {
        return doStep(tank, Battlefield::canDoLeft, Tank::doLeft);
    }

    private boolean doRight(Tank tank) {
        return doStep(tank, Battlefield::canDoRight, Tank::doRight);
    }

    private boolean doUp(Tank tank) {
        return doStep(tank, Battlefield::canDoUp, Tank::doUp);
    }

    private boolean doStep(Tank tank, BiFunction<Battlefield, Tank, Integer> function, Consumer<Tank> consumer) {
        int steps = function.apply(battlefield, tank);
        for (int step = 0; step < steps; step++) {
            consumer.accept(tank);
        }

        return steps > 0;
    }

    private boolean doShot(Tank tank) {
        shots.add(tank.doShot());
        return true;
    }

    private void repaint() {
        board.draw(CanvasTranslator.from(
            shots().map(Shot::getPoint),
            tanks.stream().flatMap(Tank::stream)));
    }

    private Stream<Shot> shots() {
        return Stream.of(myShots, shots).flatMap(Collection::stream);
    }

    private class Physics {

        private void tanksMove() {
            tanks.stream().filter(tank -> tank != myTank)
                .forEach(ai::doNextStep);

            if (tanks.size() < 5) {
                battlefield.spawn();
            }

            repaint();
        }

        private void bulletFlight() {
            shots().forEach(Shot::run);

            Map<Shot, Tank> killed = killedTanks();
            tanks.removeAll(killed.values());
            myShots.removeAll(killed.keySet());
            repaint();

            score.inc(killed.size() * 100);

            shots.stream().filter(o -> myTank.hasKilled(o))
                .findFirst().ifPresent(o -> crash(o.getPoint()));

            List<Shot> outside = shots()
                .filter(Shot::verify).collect(Collectors.toList());
            myShots.removeAll(outside);
            shots.removeAll(outside);
        }

        private Map<Shot, Tank> killedTanks() {
            return tanks.stream()
                .filter(o -> o != myTank)
                .flatMap(tank -> myShots.stream()
                    .filter(tank::hasKilled).findFirst()
                    .map(o -> new AbstractMap.SimpleEntry<>(o, tank))
                    .map(Stream::of).orElse(Stream.empty()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

    }

    private class ArtificialIntelligence {

        boolean doNextStep(Tank tank) {
            return Stream.generate(this::decideStep)
                .limit(5).anyMatch(o -> o.apply(TanksGame.this, tank));
        }

        private BiFunction<TanksGame, Tank, Boolean> decideStep() {
            switch (Random.nextInt(4)) {
                case 0:
                    return direction(Direction.random());
                case 1:
                case 2:
                    return (game, tank) -> direction(tank.getDirection()).apply(game, tank);
                case 3:
                    return TanksGame::doShot;
                default:
                    throw new IllegalStateException();
            }
        }

        private BiFunction<TanksGame, Tank, Boolean> direction(Direction direction) {
            switch (direction) {
                case UP:
                    return TanksGame::doUp;
                case RIGHT:
                    return TanksGame::doRight;
                case DOWN:
                    return TanksGame::doDown;
                case LEFT:
                    return TanksGame::doLeft;
                default:
                    throw new IllegalStateException();
            }
        }

    }

}