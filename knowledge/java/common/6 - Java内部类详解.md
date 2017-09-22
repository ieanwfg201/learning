# Java内部类详解
## 一.内部类基础
在Java中，可以将一个类定义在另一个类里面或者一个方法里面，这样的类称为内部类。广泛意义上的内部类一般
来说包括这四种：

     成员内部类
     局部内部类
     匿名内部类
     静态内部类
下面就先来了解一下这四种内部类的用法。

### 1. 成员内部类

成员内部类是最普通的内部类，它的定义为位于另一个类的内部，形如下面的形式：

     class Circle {
         double radius = 0;
         public Circle(double radius) {
             this.radius = radius;
         }
         class Draw {     //内部类
             public void drawSahpe() {
                 System.out.println("drawshape");
             }
         }
     }

这样看起来，类Draw像是类Circle的一个成员，Circle称为外部类。成员内部类可以无条件访问外部类的所有成员
属性和成员方法（包括private成员和静态成员）。

不过要注意的是，当成员内部类拥有和外部类同名的成员变量或者方法时，会发生隐藏现象，即默认情况下访问的是
成员内部类的成员。如果要访问外部类的同名成员，需要以下面的形式进行访问：

     外部类.this.成员变量
     外部类.this.成员方法
虽然成员内部类可以无条件地访问外部类的成员，而外部类想访问成员内部类的成员却不是这么随心所欲了。在外部
类中如果要访问成员内部类的成员，必须先创建一个成员内部类的对象，再通过指向这个对象的引用来访问：

     class Circle {
         private double radius = 0;
         public Circle(double radius) {
             this.radius = radius;
             getDrawInstance().drawSahpe();   //必须先创建成员内部类的对象，再进行访问
         }
         private Draw getDrawInstance() {
             return new Draw();
         }
         class Draw {     //内部类
             public void drawSahpe() {
                 System.out.println(radius);  //外部类的private成员
             }
         }
     }

成员内部类是依附外部类而存在的，也就是说，如果要创建成员内部类的对象，前提是必须存在一个外部类的对象。创建
成员内部类对象的一般方式如下：

     public class Test {
         public static void main(String[] args)  {
             //第一种方式：
             Outter outter = new Outter();
             Outter.Inner inner = outter.new Inner();  //必须通过Outter对象来创建
              
             //第二种方式：
             Outter.Inner inner1 = outter.getInnerInstance();
         }
     }
      
     class Outter {
         private Inner inner = null;
         public Outter() {
         }
         public Inner getInnerInstance() {
             if(inner == null)
                 inner = new Inner();
             return inner;
         }
         class Inner {
             public Inner() {
             }
         }
     }

内部类可以拥有private访问权限、protected访问权限、public访问权限及包访问权限。比如上面的例子，
     
     如果成员内部类Inner用private修饰，则只能在外部类的内部访问，
     如果用public修饰，则任何地方都能访问；
     如果用protected修饰，则只能在同一个包下或者继承外部类的情况下访问；
     如果是默认访问权限，则只能在同一个包下访问。这一点和外部类有一点不一样，外部类只能被public和包访问两种权限修饰。

### 2.局部内部类
局部内部类是定义在一个方法或者一个作用域里面的类，它和成员内部类的区别在于局部内部类的访问仅限于方法内或者
该作用域内。

     class People{
         public People() {
         }
     }
     class Man{
         public Man(){
         }
         public People getWoman(){
             class Woman extends People{   //局部内部类
                 int age =0;
             }
             return new Woman();
         }
     }
注意，局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的。
### 3.匿名内部类
匿名内部类应该是平时我们编写代码时用得最多的，在编写事件监听的代码时使用匿名内部类不但方便，而且使代码更加
容易维护。下面这段代码是一段Android事件监听代码：

     scan_bt.setOnClickListener(new OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     // TODO Auto-generated method stub
                 }
             });
              
             history_bt.setOnClickListener(new OnClickListener() {
                  
                 @Override
                 public void onClick(View v) {
                     // TODO Auto-generated method stub
                 }
             });

匿名内部类是唯一一种没有构造器的类。正因为其没有构造器，所以匿名内部类的使用范围非常有限，大部分匿名内部类
用于接口回调。匿名内部类在编译的时候由系统自动起名为Outter$1.class。一般来说，匿名内部类用于继承其他类或
是实现接口，并不需要增加额外的方法，只是对继承方法的实现或是重写。

### 4.静态内部类
静态内部类也是定义在另一个类里面的类，只不过在类的前面多了一个关键字static。静态内部类是不需要依赖于外部类
的，这点和类的静态成员属性有点类似，并且它不能使用外部类的非static成员变量或者方法，这点很好理解，因为在
没有外部类的对象的情况下，可以创建静态内部类的对象，如果允许访问外部类的非static成员就会产生矛盾，因为外部
类的非static成员必须依附于具体的对象。

     public class Test {
         public static void main(String[] args)  {
             Outter.Inner inner = new Outter.Inner();
         }
     }
     class Outter {
         public Outter() {
         }
         static class Inner {
             public Inner() {
             }
         }
     }

## 二.深入理解内部类
### 1.为什么成员内部类可以无条件访问外部类的成员？
在此之前，我们已经讨论过了成员内部类可以无条件访问外部类的成员，那具体究竟是如何实现的呢？下面通过反编译
字节码文件看看究竟。事实上，编译器在进行编译的时候，会将成员内部类单独编译成一个字节码文件，
下面是Outter.java的代码：

     public class Outter {
         private Inner inner = null;
         public Outter() {
         }
         public Inner getInnerInstance() {
             if(inner == null)
                 inner = new Inner();
             return inner;
         }
         protected class Inner {
             public Inner() {
                  
             }
         }
     }

反编译Outter$Inner.class文件得到下面信息：

     E:\Workspace\Test\bin\com\cxh\test2>javap -v Outter$Inner
     Compiled from "Outter.java"
     public class com.cxh.test2.Outter$Inner extends java.lang.Object
       SourceFile: "Outter.java"
       InnerClass:
        #24= #1 of #22; //Inner=class com/cxh/test2/Outter$Inner of class com/cxh/tes
     t2/Outter
       minor version: 0
       major version: 50
       Constant pool:
     const #1 = class        #2;     //  com/cxh/test2/Outter$Inner
     const #2 = Asciz        com/cxh/test2/Outter$Inner;
     const #3 = class        #4;     //  java/lang/Object
     const #4 = Asciz        java/lang/Object;
     const #5 = Asciz        this$0;
     const #6 = Asciz        Lcom/cxh/test2/Outter;;
     const #7 = Asciz        <init>;
     const #8 = Asciz        (Lcom/cxh/test2/Outter;)V;
     const #9 = Asciz        Code;
     const #10 = Field       #1.#11; //  com/cxh/test2/Outter$Inner.this$0:Lcom/cxh/t
     est2/Outter;
     const #11 = NameAndType #5:#6;//  this$0:Lcom/cxh/test2/Outter;
     const #12 = Method      #3.#13; //  java/lang/Object."<init>":()V
     const #13 = NameAndType #7:#14;//  "<init>":()V
     const #14 = Asciz       ()V;
     const #15 = Asciz       LineNumberTable;
     const #16 = Asciz       LocalVariableTable;
     const #17 = Asciz       this;
     const #18 = Asciz       Lcom/cxh/test2/Outter$Inner;;
     const #19 = Asciz       SourceFile;
     const #20 = Asciz       Outter.java;
     const #21 = Asciz       InnerClasses;
     const #22 = class       #23;    //  com/cxh/test2/Outter
     const #23 = Asciz       com/cxh/test2/Outter;
     const #24 = Asciz       Inner;
      
     {
     final com.cxh.test2.Outter this$0;
      
     public com.cxh.test2.Outter$Inner(com.cxh.test2.Outter);
       Code:
        Stack=2, Locals=2, Args_size=2
        0:   aload_0
        1:   aload_1
        2:   putfield        #10; //Field this$0:Lcom/cxh/test2/Outter;
        5:   aload_0
        6:   invokespecial   #12; //Method java/lang/Object."<init>":()V
        9:   return
       LineNumberTable:
        line 16: 0
        line 18: 9
      
       LocalVariableTable:
        Start  Length  Slot  Name   Signature
        0      10      0    this       Lcom/cxh/test2/Outter$Inner;
      
      
     }

第11行到35行是常量池的内容，下面逐一第38行的内容：

     final com.cxh.test2.Outter this$0;

这行是一个指向外部类对象的指针，看到这里想必大家豁然开朗了。也就是说编译器会默认为成员内部类添加了一个
指向外部类对象的引用，那么这个引用是如何赋初值的呢？下面接着看内部类的构造器：

     public com.cxh.test2.Outter$Inner(com.cxh.test2.Outter);

从这里可以看出，虽然我们在定义的内部类的构造器是无参构造器，编译器还是会默认添加一个参数，该参数的类型
为指向外部类对象的一个引用，所以成员内部类中的Outter this&0 指针便指向了外部类对象，因此可以在成员内
部类中随意访问外部类的成员。从这里也间接说明了成员内部类是依赖于外部类的，如果没有创建外部类的对象，则
无法对Outter this&0引用进行初始化赋值，也就无法创建成员内部类的对象了。

### 2.为什么局部内部类和匿名内部类只能访问局部final变量？

如果局部变量的值在编译期间就可以确定，则直接在匿名内部里面创建一个拷贝。如果局部变量的值无法在编译期间
确定，则通过构造器传参的方式来对拷贝进行初始化赋值。

### 3.静态内部类有特殊的地方吗？
静态内部类是不依赖于外部类的，也就说可以在不创建外部类对象的情况下创建内部类的对象。另外，静态内部类是
不持有指向外部类对象的引用的，这个读者可以自己尝试反编译class文件看一下就知道了，是没有Outter this&0
引用的。

## 三.内部类的使用场景和好处
为什么在Java中需要内部类？总结一下主要有以下四点：
1. 每个内部类都能独立的继承一个接口的实现，所以无论外部类是否已经继承了某个(接口的)实现，对于内部类都没
有影响。内部类使得多继承的解决方案变得完整，
2. 方便将存在一定逻辑关系的类组织在一起，又可以对外界隐藏。
3. 方便编写事件驱动程序
4. 方便编写线程代码

## 四.常见的与内部类相关的笔试面试题
1.根据注释填写(1)，(2)，(3)处的代码

     public class Test{
         public static void main(String[] args){
                // 初始化Bean1
                (1)
                bean1.I++;
                // 初始化Bean2
                (2)
                bean2.J++;
                //初始化Bean3
                (3)
                bean3.k++;
         }
         class Bean1{
                public int I = 0;
         }
      
         static class Bean2{
                public int J = 0;
         }
     }
      
     class Bean{
         class Bean3{
                public int k = 0;
         }
     }

（1），（2），（3）处的代码分别为：

     // (1)
     Test test = new Test();  
     Test.Bean1 bean1 = test.new Bean1(); 
     // (2)
     Test.Bean2 b2 = new Test.Bean2();
     // (3)
     Bean bean = new Bean();     
     Bean.Bean3 bean3 =  bean.new Bean3();  
     
2.下面这段代码的输出结果是什么？
     
     public class Test {
         public static void main(String[] args)  {
             Outter outter = new Outter();
             outter.new Inner().print();
         }
     }
      
      
     class Outter
     {
         private int a = 1;
         class Inner {
             private int a = 2;
             public void print() {
                 int a = 3;
                 System.out.println("局部变量：" + a);
                 System.out.println("内部类变量：" + this.a);
                 System.out.println("外部类变量：" + Outter.this.a);
             }
         }
     }
输出为：
     
     3
     2
     1

     


