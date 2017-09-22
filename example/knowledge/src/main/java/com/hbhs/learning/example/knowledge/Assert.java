package com.hbhs.learning.example.knowledge;

public class Assert {

    public static boolean isEmpty(String arg0){
        return arg0==null||arg0.trim().equals("");
    }
    public static void assertTrue(Boolean arg0){
        assertTrue(arg0, null);
    }
    public static void assertTrue(Boolean arg0, String msg){
        if (arg0==null||!arg0){
            msg = isEmpty(msg)?"Parameter '"+arg0+"' not true":msg;
            throw new RuntimeException(msg);
        }
    }
    public static void assertFalse(Boolean arg0){
        assertFalse(arg0, null);
    }
    public static void assertFalse(Boolean arg0, String msg){
        if (arg0==null||arg0){
            msg = isEmpty(msg)?"Parameter '"+arg0+"' not false":msg;
            throw new RuntimeException(msg);
        }
    }
}
