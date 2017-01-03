package com.github.vitalibo.brickgame.util;

import lombok.Getter;
import lombok.Setter;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BuilderTest {

    private EmbeddedObject sample;

    @BeforeMethod
    public void setUp() {
        sample = new EmbeddedObject();
    }

    @Test
    public void testWith() {
        Object result = Builder.of(sample)
            .with(o -> o.setId(12345))
            .with(o -> o.setArray(new String[]{"foo", "bar"}))
            .get();

        Assert.assertTrue(result == sample);
        Assert.assertEquals(sample.getId(), 12345);
        Assert.assertEquals(sample.getArray(), new String[]{"foo", "bar"});
    }

    @Test
    public void testMap() {
        long id = Builder.of(sample)
            .with(o -> o.setId(12345))
            .map(EmbeddedObject::getId)
            .get();

        Assert.assertEquals(sample.getId(), 12345);
        Assert.assertEquals(id, 12345);
    }

    private class EmbeddedObject {

        @Getter
        @Setter
        private long id;

        @Getter
        @Setter
        private String[] array = new String[2];

    }

}