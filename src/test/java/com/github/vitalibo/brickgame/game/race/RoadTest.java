package com.github.vitalibo.brickgame.game.race;

import com.github.vitalibo.brickgame.game.Point;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RoadTest {

    private Road road;

    @BeforeMethod
    public void setUp() {
        road = Road.init();
    }

    @Test
    public void testDown() {
        Point point = Point.of(road.get(road.size() - 1));

        road.doDown();
        Point npoint = Point.of(road.get(road.size() - 1));

        Assert.assertNotEquals(point.getY(), 0);
        Assert.assertEquals(npoint.getY(), 0);
        Assert.assertEquals(npoint.getX(), point.getX());
    }

}