package com.hbhs.learning.example.common;


import org.junit.Test;

public class NullPointerExceptionTest {

    @Test
    public void testCases() throws Exception {
        Integer i = null;
        int i1 = (int) i;
        System.out.println(i1);
    }
}
