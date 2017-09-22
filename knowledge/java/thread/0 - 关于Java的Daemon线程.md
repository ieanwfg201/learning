# 关于Java的Daemon线程
Java语言自己可以创建两种进程“用户线程”和“守护线程”
* 用户线程：就是我们平时创建的普通线程.
* 守护线程：主要是用来服务用户线程.

那么如何来区分这两种线程呢？其实在JDK的文档中已经说明的很清楚了:

      The Java Virtual Machine exits when the only threads running are all 
      daemon threads.
      当线程只剩下守护线程的时候，JVM就会退出.但是如果还有其他的任意一个用户线程还在，JVM就不会退出.


下面我们用实验来说明，当线程只剩下守护线程的时候,JVM就会退出.

     public class DaemonRunner implements Runnable {
         public void run() {
             while (true) {
                 for (int i = 1; i <= 100; i++) {
     
                     System.out.println(i);
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }
      
         public static void main(String[] args) {
             Thread daemonThread = new Thread(new DaemonRunner());
             // 设置为守护进程
             daemonThread.setDaemon(true);
             daemonThread.start();
             System.out.println("isDaemon = " + daemonThread.isDaemon());
             Scanner scanner = new Scanner(System.in);
             // 接受输入，使程序在此停顿，一旦接受到用户输入,main线程结束，JVM退出!
             scanner.next();  
             //AddShutdownHook方法增加JVM停止时要做处理事件：
             //当JVM退出时，打印JVM Exit语句.
             Runtime.getRuntime().addShutdownHook(new Thread(){
                   @Override
                 public void run() {
                     // TODO Auto-generated method stub
                 System.out.println("JVM Exit!");
                 }
               });
         }
     }

当程序运行的时候，Daemon线程会不断的在控制台打印数字，而main线程是一个用户线程由于”scanner.next()”
等待用户输入，属于阻塞了.此时JVM当然不会退出.

因此当用户线程main线程退出时（在本程序中,在控制台输入一个字符然后回车），如果JVM确实退出了的话，
会调用ShutDownHook在控制台上打印“JVM Exit!.”

如果没有退出的话，当然就不会打印了.

验证结果： **控制台成功打印“JVM Exit!”,JVM退出！**

另外，daemon是相于user线程而言的，可以理解为一种运行在后台的服务线程，比如时钟处理线程、idle线程、
垃圾回收线程等都是daemon线程。

daemon线程有个特点就是"比较次要"，程序中如果所有的user线程都结束了，那这个程序本身就结束了，不管daemon
是否结束。而user线程就不是这样，只要还有一个user线程存在，程序就不会退出。

用Thread中的setDaemon就可以把线程设置为Daemon

     Thread.setDaemon(true);










