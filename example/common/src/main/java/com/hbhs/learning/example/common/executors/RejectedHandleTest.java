package com.hbhs.learning.example.common.executors;

import java.util.concurrent.*;

/**
 * Created by walter on 17-9-27.
 */
public class RejectedHandleTest {

    public static void main(String[] args) throws Exception{
        RejectedHandleTest test = new RejectedHandleTest();
//        test.abortPolicy(new ThreadPoolExecutor.AbortPolicy());

//        test.abortPolicy(new ThreadPoolExecutor.CallerRunsPolicy());

        test.abortPolicy(new ThreadPoolExecutor.DiscardOldestPolicy());

//        test.abortPolicy(new ThreadPoolExecutor.DiscardPolicy());
    }

    private void abortPolicy(RejectedExecutionHandler handler){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,2,0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2), handler);

        try {
            for (int i =0;i<10;i++){
                executor.execute(new Task("task-"+i, 500));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
    }



    private static class Task implements Runnable{
        String name;
        int sleepTimeInMillSecond;
        Task(String name, int sleepTimeInMillSecond){
            this.name = name; this.sleepTimeInMillSecond = sleepTimeInMillSecond;
        }

        @Override
        public void run(){
            try {
                System.out.println(name+" is running");
                Thread.sleep(sleepTimeInMillSecond);
            }catch (Exception e){

            }
        }
    }
}
