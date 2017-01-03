package com.github.vitalibo.brickgame.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class Builder<T> {

    private final T o;

    private Builder(T o) {
        this.o = o;
    }

    public Builder<T> with(Consumer<T> consumer) {
        consumer.accept(o);
        return this;
    }

    public <M> Builder<M> map(Function<T, M> function) {
        return Builder.of(function.apply(o));
    }

    public T get() {
        return o;
    }

    public static <T> Builder<T> of(T o) {
        return new Builder<>(o);
    }

}