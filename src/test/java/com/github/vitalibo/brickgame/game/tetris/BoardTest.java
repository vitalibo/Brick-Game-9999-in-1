package com.github.vitalibo.brickgame.game.tetris;

import com.github.vitalibo.brickgame.game.Point;
import com.github.vitalibo.brickgame.game.tetris.tetromino.Factory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BoardTest {

    private Board board;

    @BeforeMethod
    public void setUp() {
        board = Board.init(0);
    }

    @DataProvider
    public Object[][] levels() {
        return new Object[][]{
            {5}, {7}, {9}, {15}
        };
    }

    @Test(dataProvider = "levels")
    public void testInit(int level) {
        board = Board.init(level);

        boolean matchInTop = anyMatchInRange(board, 0, 20 - level);
        boolean matchInBottom = anyMatchInRange(board, 20 - level, 20);

        Assert.assertFalse(matchInTop);
        Assert.assertTrue(matchInBottom);
    }

    @Test
    public void testAdd() {
        Tetromino points = Factory.generate();

        board.add(points);

        Assert.assertEquals(board, points);
    }

    @Test
    public void testVerify() {
        List<Point> points = Factory.generate();

        board.add(points.get(0));

        Assert.assertFalse(board.verify(points));
    }

    @Test
    public void testCleanUp() {
        board.addAll(Arrays.asList(
            Point.of(16, 5), Point.of(13, 4)));
        board.addAll(IntStream.range(0, 10)
            .mapToObj(i -> Stream.of(Point.of(15, i), Point.of(14, i)))
            .flatMap(stream -> stream)
            .collect(Collectors.toList()));

        int line = board.cleanUp();

        Assert.assertEquals(line, 2);
        Assert.assertTrue(board.containsAll(
            Arrays.asList(Point.of(15, 4), Point.of(15, 4))));
    }

    @Test
    public void testIsFull() {
        Assert.assertFalse(board.isFull());

        board.add(Point.of(-1, 0));

        Assert.assertTrue(board.isFull());
    }

    private static boolean anyMatchInRange(List<Point> board, int startInclusive, int endExclusive) {
        return IntStream.range(startInclusive, endExclusive)
            .anyMatch(y -> IntStream.range(0, 10)
                .mapToObj(x -> Point.of(y, x))
                .anyMatch(board::contains));
    }

}