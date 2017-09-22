# hashcode & equals之5重天
## 何时需要重写equals()
当一个类有自己特有的“逻辑相等”概念（不同于对象身份的概念）。

## 如何覆写equals()和hashcode
覆写equals方法
* 使用instanceof操作符检查“实参是否为正确的类型”。
* 对于类中的每一个“关键域”，检查实参中的域与当前对象中对应的域值。
* 对于非float和double类型的原语类型域，使用==比较；
* 对于对象引用域，递归调用equals方法；
* 对于float域，使用Float.floatToIntBits(afloat)转换为int，再使用==比较；
* 对于double域，使用Double.doubleToLongBits(adouble)转换为int，再使用==比较；
* 对于数组域，调用Arrays.equals方法。

覆写hashcode
* 把某个非零常数值，例如17，保存在int变量result中；
* 对于对象中每一个关键域f（指equals方法中考虑的每一个域）：
* boolean型，计算(f? 0 : 1);
* byte,char,short型，计算(int);
* long型，计算(int)(f ^ (f>>>32));
* float型，计算Float.floatToIntBits(afloat);
* double型，计算Double.doubleToLongBits(adouble)得到一个long，再执行[2.3];
* 对象引用，递归调用它的hashCode方法;
* 数组域，对其中每个元素调用它的hashCode方法。
* 将上面计算得到的散列码保存到int变量c，然后执行result=37*result+c;
* 返回result。

## 当改写equals()的时候，总是要改写hashCode()
根据一个类的equals方法（改写后），两个截然不同的实例有可能在逻辑上是相等的，但是，根据
Object.hashCode方法，它们仅仅是两个对象。因此，违反了“相等的对象必须具有相等的散列码”。

## 两个对象如果equals相等那么这两个对象的hashcode一定相等，如果两个对象的hashcode相等那么这两个对象是否一定equals?
回答是不一定，这要看这两个对象有没有重写Object的hashCode方法和equals方法。如果没有重写，
是按Object默认的方式去处理。

试想我有一个桶，这个桶就是hashcode，桶里装的是西瓜我们认为西瓜就是object，有的桶是一个桶
装一个西瓜，有的桶是一个桶装多个西瓜。

比如String重写了Object的hashcode和equals，但是两个String如果hashcode相等，那么equals
比较肯定是相等的，但是“==”比较却不一定相等。如果自定义的对象重写了hashCode方法，有可能hashcode
相等，equals却不一定相等，“==”比较也不一定相等。

**此处考的是你对object的hashcode的意义的真正的理解！！！如果作为一名高级开发人员
或者是架构师，必须是要有这个概念的，否则，直接ban掉了。**

## 为什么我们在定义hashcode时如： h = 31*h + val[off++];  要使用31这个数呢？
我们来看这个要命的31这个系数为什么总是在里面乘啊乘的？为什么不适用32或者其他数字？选择31
原因是因为31是一个素数！

素数在使用的时候有一个作用就是如果我用一个数字来乘以这个素数，那么最终的出来的结果只能被
素数本身和被乘数还有1来整除！如：我们选择素数3来做系数，那么3*n只能被3和n或者1来整除，
我们可以很容易的通过3n来计算出这个n来。这应该也是一个原因！


## 在存储数据计算hash地址的时候，我们希望尽量减少有同样的hash地址，所谓“冲突”。
31是个神奇的数字，因为任何数n * 31就可以被JVM优化为 (n << 5) -n,移位和减法的操作效率要比
乘法的操作效率高的多，对左移现在很多虚拟机里面都有做相关优化，并且31只占用5bits！



