package com.github.vitalibo.brickgame.core;

import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CountdownJobTest {

    private Kernel.CountdownJob job;
    private int invocations;
    private boolean finished;

    @BeforeMethod
    public void setUp() {
        invocations = 0;
        Kernel kernel = new Kernel();
        job = kernel.new CountdownJob(
            new Object(), 5, j -> invocations++, 10, j -> finished = true);
    }

    @Test
    public void testCountdown() {
        Assert.assertFalse(finished);
        Assert.assertTrue(invocations < 10);

        sleep();
        Assert.assertTrue(finished);
        Assert.assertTrue(job.isKilled());
        Assert.assertEquals(invocations, 10);
    }

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(100);
    }

}