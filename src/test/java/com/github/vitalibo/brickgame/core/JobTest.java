package com.github.vitalibo.brickgame.core;

import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JobTest {

    private Kernel.Job job;
    private int invocations;

    @BeforeMethod
    public void setUp() {
        invocations = 0;
        Kernel kernel = new Kernel();
        job = kernel.new Job(new Object(), 20, j -> invocations++);

        sleep();
    }

    @Test
    public void testKill() {
        Assert.assertFalse(job.isKilled());

        job.kill();
        int count = invocations;

        sleep();
        Assert.assertEquals(invocations, count);
        Assert.assertTrue(job.isKilled());
    }

    @Test
    public void testPause() {
        Assert.assertFalse(job.isPause());

        job.setPause(true);
        int count = invocations;

        sleep();
        Assert.assertEquals(invocations, count);
        Assert.assertTrue(job.isPause());

        job.setPause(false);

        sleep();
        Assert.assertNotEquals(invocations, count);
        Assert.assertFalse(job.isPause());
    }

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(50);
    }

}