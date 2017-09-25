# Random Tensors - 随机Tensor对象
TensorFlow提供了一些操作(operations)以不同的分配方式来创建不同的随机数．这些操作是稳定的，并且每次创建的随机值也是可评估的

在这些函数中，参数seed和图级别(Graph-level)的随机seed协调使用．如果需要修改如下函数中的seed值，可以使用如图级别(Graph-level)的函数set_random_seed或者操作级别(op-level)的seed参数来实现

无论是设置Graph-level的seed还是op-level的seed，最终修改的都将影响所有的函数(操作)，可参考方法set_random_seed了解详细使用方法
* tf.random_normal(shape, mean=0.0, stddev=1.0, dtype=tf.float32, seed=None, name=None)
* tf.truncated_normal(shape, mean=0.0, stddev=1.0, dtype=tf.float32, seed=None, name=None)
* tf.random_uniform(shape, minval=0.0, maxval=1.0, dtype=tf.float32, seed=None, name=None)
* tf.random_shuffle(value, seed=None, name=None)
* tf.set_random_seed(seed)
## tf.random_normal(shape, mean=0.0, stddev=1.0, dtype=tf.float32, seed=None, name=None)
生成符合正态分布(高斯分布)的一些随机值，其符合条件为：
>     所生成的数值为随机生成，且符合正态分布
>     其分布的均值为mean，未设置时默认为０
>     其分布的标准差为stddev，未设置时默认为１
>     所生成结果类型为dtype, 未设置时默认为float32
* 参数:
  * shape: 返回tensor对象的阶，类型为int数组或者一个一阶的tensor　integer类型
  * mean: 正态分布的均值，数值或者tensor的数值类型，默认均值为0
  * stddev: 正态分布的标准差，数值或者tensor的数值类型，默认标准差为１
  * dtype: 返回tensor对象的数值类型，默认为tf.float32
  * seed: int类型，用于为该分布生成一个seed，参考set_random_seed方法
  * name: 别名(可选)
* 返回:
  * 一个符合正态分布，阶为shape随机生成值的tensor对象
常用调用方法：
````

````
## tf.truncated_normal(shape, mean=0.0, stddev=1.0, dtype=tf.float32, seed=None, name=None)
生成符合截断正态分布(截尾正态分布)的一些随机值，其符合条件为：
>     所生成的数值为随机生成，且符合正态分布
>     其分布的均值为mean，未设置时默认为０
>     其分布的标准差为stddev，未设置时默认为１
>     所生成结果类型为dtype, 未设置时默认为float32
>     当所生成的值与均值差大于两个标准差的时候，改值将被抛弃并重新选择(也就是说生成的所有值必然是在两个标准差间，相对集中)

* 参数:
  * shape: 返回tensor对象的阶，类型为int数组或者一个一阶的tensor　integer类型
  * mean: 正态分布的均值，数值或者tensor的数值类型，默认均值为0
  * stddev: 正态分布的标准差，数值或者tensor的数值类型，默认标准差为１
  * dtype: 返回tensor对象的数值类型，默认为tf.float32
  * seed: int类型，用于为该分布生成一个seed，参考set_random_seed方法
  * name: 别名(可选)
* 返回:
  * 一个符合截断正态分布，阶为shape随机生成值的tensor对象
常用调用方法：
````

````
## tf.random_uniform(shape, minval=0.0, maxval=1.0, dtype=tf.float32, seed=None, name=None)
生成符合均匀分布的一些随机值，其符合条件为：
>     所生成的值在[minval,maxval)之间，包含minval,不包含maxval
>     
>     
>     
* 参数:
  * shape: 返回tensor对象的阶，类型为int数组或者一个一阶的tensor　integer类型
  * minval: 最小值，包含改值．默认为0
  * maxval: 最大值，不包含改值．默认为１
  * dtype: 返回tensor对象的数值类型，默认为tf.float32
  * seed: int类型，用于为该分布生成一个seed，参考set_random_seed方法
  * name: 别名(可选)
* 返回:
  * 一个符合均匀分布，阶为shape随机生成值的tensor对象
常用调用方法：
````

````
## tf.random_shuffle(value, seed=None, name=None)
将value按照第一个维度(dimension 0)随机洗牌使之混乱，任何一个value[i]都将映射到并仅映射到一个输出值output[j]，最终放入的序列j是随机的

* 参数:
  * value: 待洗牌的tensor对象
  * seed: int类型，用于生成一个seed，参考set_random_seed方法
  * name: 别名(可选)
* 返回:
  * 类型同value，随机洗牌后的tensor对象
  
常用调用方法：
````
data = tf.constant([1,2,3,4,5], shape=[5])
tf.random_shuffle(data)
data = tf.constant([0,1,2,3,4,5,6,7,8,9],shape=[5,2])
tf.random_shuffle(data)
````
其最终结果为随机的
````
data = tf.constant([1,2,3,4,5], shape=[5])
p.printValue("Original", data)
p.printValue("random_shuffle_1",tf.random_shuffle(data))
p.printValue("random_shuffle_2",tf.random_shuffle(data))
p.printValue("random_shuffle_3",tf.random_shuffle(data))

data = tf.constant([0,1,2,3,4,5,6,7,8,9],shape=[5,2])
p.printValue("Original", data)
p.printValue("random_shuffle_1",tf.random_shuffle(data))
p.printValue("random_shuffle_2",tf.random_shuffle(data))
p.printValue("random_shuffle_3",tf.random_shuffle(data))
````
返回结果可能如下：
````
# Original : Tensor("Const_2:0", shape=(5,), dtype=int32) - 
[1 2 3 4 5]
# random_shuffle_1 : Tensor("RandomShuffle_2:0", shape=(5,), dtype=int32) - 
[2 3 4 5 1]
# random_shuffle_2 : Tensor("RandomShuffle_3:0", shape=(5,), dtype=int32) - 
[1 2 4 3 5]
# random_shuffle_3 : Tensor("RandomShuffle_4:0", shape=(5,), dtype=int32) - 
[2 5 1 3 4]
# Original : Tensor("Const_3:0", shape=(5, 2), dtype=int32) - 
[[0 1]
 [2 3]
 [4 5]
 [6 7]
 [8 9]]
# random_shuffle_1 : Tensor("RandomShuffle_5:0", shape=(5, 2), dtype=int32) - 
[[2 3]
 [4 5]
 [6 7]
 [0 1]
 [8 9]]
# random_shuffle_2 : Tensor("RandomShuffle_6:0", shape=(5, 2), dtype=int32) - 
[[0 1]
 [4 5]
 [8 9]
 [6 7]
 [2 3]]
# random_shuffle_3 : Tensor("RandomShuffle_7:0", shape=(5, 2), dtype=int32) - 
[[6 7]
 [8 9]
 [2 3]
 [0 1]
 [4 5]]
````
## tf.set_random_seed(seed)
设置图级别(Graph-level)随机seed值

传入seed值分为两种方式：图级别(Graph-level)和操作级别(op-level)．该方法是设置图级别(Graph-level)的

该方法和操作级别(op-level)方法的seed是通过如下方式一起作用的
  1. 如果图级别(Graph-level)和操作级别(op-level)都没有设置seed值：那么当前操作(op)会使用一个随机的seed值
  2. 如果图级别(Graph-level)设置，但是操作级别(op-level)没有设置：那么系统将为当前操作(op)创建一个随机的seed，并同图级别(Graph-level)的seed值一起生成一个确定的序列
  3. 如果图级别(Graph-level)没有设置，但是操作级别(op-level)设置：那么一个默认的图级别(Graph-level)的seed被设置来和当前操作(op)一起来生成一个确定的序列(不同的op使用的是同样的一个默认的Graph-level的seed)
  4. 如果图级别(Graph-level)和操作级别(op-level)都设置seed值：那么两个seed会一起用来生成一个确定的序列

总结如下：
>     同一个session内，无论是否设置seed，每次生成的随机值都是不一致的
>     当图级别(Graph-level)和操作级别(op-level)都没有设置seed值时，任何session的任何一次op都会产生不一样的随机值
>     当任意级别的seed设置后，每一个session产生的序列应该都是一致的
  
* 参数:
  * seed: int类型，对应的seed值
* 返回:
  * 无
  
调用方法：

    print "#####  Graph-level-seed: NO, Op-level-sedd: NO"
    a = tf.random_uniform([5])
    with tf.Session() as sess1:
        print "Session 1 - random_uniform"
        print sess1.run(a)
        print sess1.run(a)
    
    with tf.Session() as sess2:
        print "Session 2 - random_uniform"
        print sess2.run(a)
        print sess2.run(a)
    
    print ""
    print "#####  Graph-level-seed: NO, Op-level-sedd: YES"
    a = tf.random_uniform([5], seed=1)
    with tf.Session() as sess1:
        print "Session 1 - random_uniform"
        print sess1.run(a)
        print sess1.run(a)
    
    with tf.Session() as sess2:
        print "Session 2 - random_uniform"
        print sess2.run(a)
        print sess2.run(a)
    
    
    print ""
    print "#####  Graph-level-seed: YES, Op-level-sedd: NO"
    tf.set_random_seed(123)
    a = tf.random_uniform([5])
    with tf.Session() as sess1:
        print "Session 1 - random_uniform"
        print sess1.run(a)
        print sess1.run(a)
    
    with tf.Session() as sess2:
        print "Session 2 - random_uniform"
        print sess2.run(a)
        print sess2.run(a)
    
    
    print ""
    print "#####  Graph-level-seed: YES, Op-level-sedd: YES"
    tf.set_random_seed(123)
    a = tf.random_uniform([5], seed=1)
    with tf.Session() as sess1:
        print "Session 1 - random_uniform"
        print sess1.run(a)
        print sess1.run(a)
    
    with tf.Session() as sess2:
        print "Session 2 - random_uniform"
        print sess2.run(a)
        print sess2.run(a)


返回结果为：

    #####  Graph-level-seed: NO, Op-level-sedd: NO
    Session 1 - random_uniform
    [ 0.9420141   0.85354745  0.94107366  0.44018006  0.55539083]
    [ 0.84141016  0.49817741  0.14080584  0.24531007  0.00651419]
    Session 2 - random_uniform
    [ 0.04167759  0.61449087  0.35584712  0.46991599  0.05515969]
    [ 0.26847517  0.54951859  0.62234747  0.46934938  0.51899087]
    
    #####  Graph-level-seed: NO, Op-level-sedd: YES
    Session 1 - random_uniform
    [ 0.23903739  0.92039955  0.05051243  0.49574447  0.83552229]
    [ 0.93370306  0.71884167  0.85377777  0.77928996  0.52497303]
    Session 2 - random_uniform
    [ 0.23903739  0.92039955  0.05051243  0.49574447  0.83552229]
    [ 0.93370306  0.71884167  0.85377777  0.77928996  0.52497303]
    
    #####  Graph-level-seed: YES, Op-level-sedd: NO
    Session 1 - random_uniform
    [ 0.51124465  0.36002755  0.05922031  0.50877547  0.67813873]
    [ 0.35790503  0.20084631  0.74576187  0.72885048  0.80195117]
    Session 2 - random_uniform
    [ 0.51124465  0.36002755  0.05922031  0.50877547  0.67813873]
    [ 0.35790503  0.20084631  0.74576187  0.72885048  0.80195117]
    
    #####  Graph-level-seed: YES, Op-level-sedd: YES
    Session 1 - random_uniform
    [ 0.74299872  0.65421712  0.58962417  0.71111512  0.57569849]
    [ 0.16465187  0.68685746  0.71193647  0.64175022  0.97543263]
    Session 2 - random_uniform
    [ 0.74299872  0.65421712  0.58962417  0.71111512  0.57569849]
    [ 0.16465187  0.68685746  0.71193647  0.64175022  0.97543263]

