package com.hbhs.learning.example.thread;

import org.junit.Test;

public class ThreadStatus {

    @Test
    public void test() throws Exception {
//        basicRunning();
        testThreadSleep();
    }

    private void basicRunning() throws Exception {
        final String name = "Thread-Normal";
        Thread thread = new Thread() {
            public void run() {
                super.setName(name);
                printThread(this, "running logic now.");
            }
        };
        printThread(thread, "thread init.");
        thread.start();
        printThread(thread, "thread after start.");
        Thread.sleep(1000);
        printThread(thread, "Main thread after sleep.");
    }

    private void testThreadSleep() throws Exception {
        final String name = "Thread-Interrupt";
        Thread thread = new Thread(){
            public void run(){
                super.setName(name);
                printThread(this, "runnint logic now.");
                try {
                    sleep(1000);
                }catch (Exception e){}
            }
        };
        thread.start();
        printThread(thread, "Checking thread status.");
        Thread.sleep(500);
        printThread(thread, "Checking thread status.");
        Thread.sleep(1000);
        printThread(thread, "Checking thread status.");
    }

    private void testWaitNotify() throws Exception{
        final String name = "Thread-Wait&Notify";
        Thread thread = new Thread(){
            @Override
            public void run(){
                super.setName(name);
            }
        };
    }

    protected static void printThread(Thread thread, String otherInfos) {
        StringBuilder str = new StringBuilder(thread.getName());
        str.append("[").append(thread.getState()).append("]");
        str.append(" ").append(otherInfos);
        System.out.println(str);
    }
}
