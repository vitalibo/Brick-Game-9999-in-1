package com.github.vitalibo.brickgame.core;

import lombok.SneakyThrows;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public class KernelTest {

    @Spy
    private Kernel spyKernel;
    @Mock
    private Consumer<? super Kernel.Job> mockConsumer;

    private Kernel.Job job;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testJob() {
        String name = this.getClass().getName();

        job = spyKernel.new Job(this, 10, mockConsumer);
        sleep();

        Assert.assertEquals(job.getName(), name);
        Assert.assertEquals(spyKernel.get(name), job);
        Mockito.verify(mockConsumer, Mockito.atLeast(1)).accept(job);
        Mockito.verify(spyKernel).schedule(job, 0L, 10L);
    }

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(100);
    }

}