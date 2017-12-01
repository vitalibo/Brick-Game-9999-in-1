package com.github.vitalibo.brickgame.game.tanks;

import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.game.Shape;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@EqualsAndHashCode(of = "uuid")
class Tank implements List<Point> {

    private static final Shape[] STATES = new Shape[]
        {
            Shape.of(3, 0x2, 0x7, 0x5),
            Shape.of(3, 0x6, 0x3, 0x6),
            Shape.of(3, 0x5, 0x7, 0x2),
            Shape.of(3, 0x3, 0x6, 0x3)
        };

    private final UUID uuid;

    @Getter
    private Point point;
    @Getter
    private Direction direction;
    @Delegate
    private List<Point> points;

    Tank(Tank o) {
        this(o.uuid, Point.of(o.point), o.direction);
    }

    Tank(Point point, Direction direction) {
        this(UUID.randomUUID(), point, direction);
    }

    Tank(UUID uuid, Point point, Direction direction) {
        this.uuid = uuid;
        this.point = point;
        this.direction = direction;
        this.points = state(point, direction.ordinal());
    }

    public void doDown() {
        doStep(Direction.DOWN);
    }

    public void doLeft() {
        doStep(Direction.LEFT);
    }

    public void doRight() {
        doStep(Direction.RIGHT);
    }

    public void doUp() {
        doStep(Direction.UP);
    }

    private void doStep(Direction dr) {
        if (dr == this.direction) {
            points.forEach(dr);
            dr.accept(point);
            return;
        }

        points = state(point, dr.ordinal());
        this.direction = dr;
    }

    public boolean verify() {
        return (point.getX() < 0 || point.getX() > 7)
            || (point.getY() < 0 || point.getY() > 17);
    }

    public Shot doShot() {
        return new Shot(
            Point.of(
                point.getY() + 1 + shifting(direction, Direction.UP, Direction.DOWN),
                point.getX() + 1 + shifting(direction, Direction.LEFT, Direction.RIGHT)),
            direction);
    }

    public boolean hasKilled(Shot shot) {
        return points.contains(shot.getPoint());
    }

    private static List<Point> state(Point point, int state) {
        return STATES[state].apply(point)
            .collect(Collectors.toList());
    }

    private static int shifting(Direction dr, Direction lower, Direction upper) {
        return dr == lower ? -1 : dr == upper ? 1 : 0;
    }

}