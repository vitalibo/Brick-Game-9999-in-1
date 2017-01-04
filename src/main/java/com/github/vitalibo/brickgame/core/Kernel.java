package com.github.vitalibo.brickgame.core;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Kernel extends Timer implements Map<String, Kernel.Job> {

    @Delegate
    private final Map<String, Job> jobs;

    public Kernel() {
        jobs = new ConcurrentHashMap<>();
    }

    public Kernel.Job job(Object o, long milliseconds, Consumer<Job> action) {
        return new Job(o, milliseconds, action);
    }

    public Kernel.Job job(Object o, long milliseconds, Consumer<CountdownJob> action,
                          int countdown, Consumer<CountdownJob> finalize) {
        return new CountdownJob(o, milliseconds, action, countdown, finalize);
    }

    public class Job extends TimerTask {

        @Getter
        private final String name;

        private final Consumer<? super Job> action;

        @Getter
        @Setter
        private boolean pause;
        @Getter
        private boolean killed;

        Job(Object o, long milliseconds, Consumer<? super Job> action) {
            this.name = o.getClass().getName();
            this.action = action;
            jobs.put(name, this);
            schedule(this, 0, milliseconds);
        }

        @Override
        public void run() {
            if (pause) {
                return;
            }

            action.accept(this);
        }

        public boolean kill() {
            jobs.remove(name);
            killed = true;
            return cancel();
        }

    }

    public class CountdownJob extends Job {

        private final Consumer<CountdownJob> finalize;

        @Getter
        private int countdown = 100;

        CountdownJob(Object o, long milliseconds, Consumer<? super CountdownJob> action,
                     int countdown, Consumer<CountdownJob> finalize) {
            super(o, milliseconds, job -> action.accept((CountdownJob) job));
            this.countdown = countdown;
            this.finalize = finalize;
        }

        @Override
        public void run() {
            if (isPause()) {
                return;
            }

            if (--countdown < 0) {
                this.kill();
                // TODO : fix NullPointerException
                finalize.accept(this);
                return;
            }

            super.run();
        }

    }

}