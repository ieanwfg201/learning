package com.hbhs.learning.example.thread;


import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentModificationExceptionExample {
    private static final String SUCCESS_MESSAGE = "SUCCESS";

    @Test
    public void testAll() throws Exception{
        final Set<Integer> set = new HashSet<>();
        final Map<String, Integer> messageMap = new ConcurrentHashMap<>();
        Thread addThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        int index = 0;
                        while(index<10000){
                            set.add(index);
                        }
                    }catch (Exception e){
                        putMessage(messageMap, "ADD", e.getMessage());
                    }

                }
            }
        };
        Thread removeThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        int index = 0;
                        while(index<10000){
                            int i = (int)(Math.random()*10000);
                            if (set.contains(i)) set.remove(i);
                        }
//                        sleep(100);
                    }catch (Exception e){
                        putMessage(messageMap, "REMOVE", e.getMessage());
                    }

                }
            }
        };
        Thread addAllThread = new Thread(){
            @Override
            public void run(){
                Set<Integer> current = new HashSet<>();
                while(true){
                    try {
                        current.addAll(set);
                    }catch (Exception e){
                        putMessage(messageMap, "ADD", e.getMessage());
                    }
                    current.clear();
                }
            }
        };
        addThread.start();
        removeThread.start();
        addAllThread.start();



        while(true){
            for (String key : messageMap.keySet()) {
                System.out.println(key+": "+messageMap.get(key));
            }
            Thread.sleep(1000);
        }
    }

    private final void putMessage(Map<String, Integer> map, String action, String message){
        synchronized ("dddd"){
            String key = action+"_"+message;
            if (!map.containsKey(key)){
                map.put(key, 0);
            }
            map.put(key, map.get(key)+1);
        }
    }
}
