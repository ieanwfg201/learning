#  Java main方法详细解析

## 1. main方法说起，为什么必须是这种格式
当编译完java文件后(javac)，需要有个一含有main方法的类，java 命令将指示操作系统启动一个jvm进程
这个jvm进程启动后，寻找那个main地方开始执行程序

     java [JVM_Options] ClassName_with_main [args_separate_space]
     
其中main方法的签名必须是 

     pubic static void main(String[] args)
     
那么为什么必须是这样结构呢？

### 1.1 为什么main方法是静态的（static）
1. 正因为main方法是静态的，JVM调用这个方法就不需要创建任何包含这个main方法的实例。
2. 因为C和C++同样有类似的main方法作为程序执行的入口。
3. 如果main方法不声明为静态的，JVM就必须创建main类的实例，因为构造器可以被重载，JVM就没法确定调用
哪个main方法。
4. 静态方法和静态数据加载到内存就可以直接调用而不需要像实例方法一样创建实例后才能调用，如果main方法
是静态的，那么它就会被加载到JVM上下文中成为可执行的方法。

### 1.2 为什么main方法是公有的（public）
Java指定了一些可访问的修饰符如：private、protected、public，任何方法或变量都可以声明为public，
Java可以从该类之外的地方访问。因为main方法是公共的，JVM就可以轻松的访问执行它。
### 1.3 为什么main方法没有返回值（Void）
因为main返回任何值对程序都没任何意义，所以设计成void，意味着main不会有任何值返回

简单点:
* 首先，main方法是JVM（java虚拟机）自动调用．JVM调用main方法的位置自然不会在某个类中、或某个包中，
因此只有当main方法在公有级别上时，才对JVM可见，所以mian方法需要public修饰，main方法所在的类也需要
public修饰符。
* 由于main方法是所有程序的入口，也就是main被调用时没有任何对象创建，不通过对象调用某一方法，只有将
该方法定义为静态方法，所以main方法是一个静态方法，既需要static修饰。
* JVM对于java程序已经是最底层，由它调用的方法的返回值已经没有任何地方可去，因此，main方法返回值为空
，既需用void修饰。
* 至于main方法的参数String[ ] arg我们现在已经很少有机会去用它了，它用于在接受命令行传入的参数

总结如下：

1. main方法必须声明为public、static、void，否则JVM没法运行程序
2. 如果JVM找不到main方法就抛出NoSuchMethodError:main异常，例如：如果你运行命令：java HelloWrold，
JVM就会在HelloWorld.class文件中搜索public static void main (String[] args) 方法
3. main方式是程序的入口，程序执行的开始处。
4. main方法被一个特定的线程”main”运行，程序会一直运行直到main线程结束或者non-daemon线程终止。
5. 当你看到“Exception in Thread main”如：Excpetion in Thread main:Java.lang.NullPointedException ,
意味着异常来自于main线程，你可以声明main方法使用java1.5的可变参数的方式如：
     
>　　　　public static void main(String... args)

6. 除了static、void、和public，你可以使用final，synchronized、和strictfp修饰符在main方法的签名中，如：
     
>        public strictfp final synchronized static void main(String[] args)

7. main方法在Java可以像其他方法一样被重载，但是JVM只会调用上面这种签名规范的main方法。
8. 你可以使用throws子句在方法签名中，可以抛出任何checked和unchecked异常
9. 静态初始化块在JVM调用main方法前被执行，它们在类被JVM加载到内存的时候就被执行了。

## 2. 执行main方法之前发生了

可以参看 [jvm源码分析](http://www.blogjava.net/sslaowan/archive/2012/04/26/376701.html)

首先要明确 jvm进程 是操作系统的进程，该进程是多线程机制的，我们明确两种线程：
     
     jvm线程：指jvm自行管理的线程，我们在程序中无法操控，多是守护类型的
     java线程：指从java技术角度看 jvm、我们在程序中用Thread类或Runnable接口编写产生的线程，可操控的线程

至于 java线程 在 jvm里面是怎么实现的，怎么对应到os级别的线程的，请看[java内存模型与线程]{https://my.oschina.net/jingxing05/blog/275334}

明确两类不同的线程之后，执行main方法之前： LoadJavaVM，jvm进程启动了多个jvm线程（很可能是错的，如有，请赐教）：

jvm线程：
* 启动 VM Thread， 单例的，所有线程之始祖！这个线程自轮询loop从对一个队列中取操作任务，来产生其他线程
* 根据jvm抽象规范，可能有执行引擎线程，GC线程，classloader线程

在jvm自身启动和初始化之后，会ContinueInNewThread(JavaMain, threadStackSize, (void*)&args);
即启动一个叫main的线程来执行  入口的main方法，main线程虽然不是我们手动生出的线程，但ta还是一个非守护线程

## 3. main执行过程
### 加载类
执行main方法时，jvm进程发现main所在类没有在方法区，于是开始进行classload，
类加载完的最后一步是根据情况决定是不是要进行类的初始化
     
     在main执行之前，必须先对类进行初始化。初始化类的变量，还有静态代码块。初始化的时候还要先初始化它的父类。每个类都有一个隐含的父类Object。  
     初始化的顺序：类变量和静态块按序，先父后子
     
     类的初始化过程发生时刻： 
     1. T是一个类，当T的一个实例创建的时候，也就是T t = new T（）; 
     2. T的一个静态方法被调用的时候，也就是 T.staticField（）; 
     3. T的静态属性被赋值的时候，T.staticField = o; 
     4. T的一个静态属性被使用的时候，也就是 Object o = T.staticField; 但是它不是常量。 
     5. T is a top level class , and an assert statement  lexically nested within T  is executed. （不懂，求解） 

### 执行main方法
将方法需要的参数，局部变量，本地方法，操作数等以 栈帧的结构 push到 main线程的堆栈区，然后执行引擎线程开始执行，
执行完毕，将该栈帧 pop掉。main线程的堆栈区没有栈帧时，main线程消退。

### 卸载类对象
这一步是个优化的步骤，释放一些方法区的内存，jvm自己决定要不要这一步，一般不会去卸载方法区的
### 程序退出
1. 所有的非daemon线程都终止了 
2. 某个线程调用了类Runtime或者System的exit方法

main程序执行图
![main程序执行图](file://./pic/2_1.jpg)

类加载详细过程
![类加载详细过程](file://./pic/2_2.jpg)

