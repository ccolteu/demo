package com.cc.demo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UnitTestMathUtils {

    @Test
    public void add_MathUtils() {
        int sum = MathUtils.add(3, 4);
        assertThat(sum, is(7));
    }
}
