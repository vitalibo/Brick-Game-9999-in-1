package com.github.vitalibo.brickgame.game;

import com.github.vitalibo.brickgame.core.*;
import com.github.vitalibo.brickgame.core.Number;
import com.github.vitalibo.brickgame.util.CanvasTranslator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
class Crash {

    private static final Shape[] STATES;

    static {
        STATES = new Shape[]{
            Shape.of(new int[][]{{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}}),
            Shape.of(new int[][]{{0, 0, 0, 0, 0}, {0, 1, 1, 1, 0}, {0, 1, 0, 1, 0}, {0, 1, 1, 1, 0}, {0, 0, 0, 0, 0}}),
            Shape.of(new int[][]{{1, 0, 1, 0, 1}, {0, 0, 0, 0, 0}, {1, 0, 0, 0, 1}, {0, 0, 0, 0, 0}, {1, 0, 1, 0, 1}})};
    }

    private final Canvas board;
    private final Point point;
    private final List<Point> mask;
    private final boolean[][] screenshot;

    private Crash(Context context, Point point) {
        this(context.getBoard(), point, makeMask(context, point), context.getBoard().get());

        init(context);
    }

    private void init(Context context) {
        final Controller controller = context.getController();
        controller.lock();

        final Kernel kernel = context.getKernel();
        kernel.forEach((s, job) -> job.kill());
        kernel.job(this, 50, this::paint, 30, (i) ->
            kernel.job(this, 25, this::cleanup, 20, (job) ->
                restart(controller, context.getLife())));
    }

    private void paint(Kernel.CountdownJob job) {
        Stream<Point> state = STATES[job.getCountdown() % STATES.length]
            .apply(point)
            .peek(point -> point.doMove(-2, -2));

        board.draw(CanvasTranslator.from(mask.stream(), state));
    }

    private void cleanup(Kernel.CountdownJob job) {
        IntStream.range(0, 10)
            .forEach(w -> screenshot[job.getCountdown()][w] = true);

        board.draw(screenshot);
    }

    private static void restart(Controller controller, Number life) {
        controller.unlock();

        if (life.get() <= 0) {
            controller.init(Menu.class);
            return;
        }

        controller.reinit();
    }

    static Crash on(Context context, Point point) {
        return new Crash(context, point);
    }

    private static List<Point> makeMask(Context context, Point point) {
        Set<Point> rectangle = rectangle(point);
        return IntStream.range(0, 20)
            .mapToObj(h -> IntStream.range(0, 10)
                .filter(w -> context.getBoard().get()[h][w])
                .filter(w -> !rectangle.contains(Point.of(h, w)))
                .mapToObj(w -> Point.of(h, w)))
            .flatMap(s -> s)
            .collect(Collectors.toList());
    }

    private static Set<Point> rectangle(Point point) {
        return IntStream.range(-2, 3)
            .mapToObj(h -> IntStream.range(-2, 3)
                .mapToObj(w -> Point.of(point.getY() + h, point.getX() + w)))
            .flatMap(stream -> stream)
            .collect(Collectors.toSet());
    }

}