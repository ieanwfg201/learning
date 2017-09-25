# Constant Value tensor - tensor常量
TensorFlow提供了一些操作用于生成常量(字符串，数字，数列，维度模型等)．TensorFlow提供的创建常量方法主要包括：
* tf.zeros(shape, dtype=tf.float32, name=None)
* tf.zeros_like(tensor, dtype=None, name=None)
* tf.ones(shape, dtype=tf.float32, name=None)
* tf.ones_like(tensor, dtype=None, name=None)
* tf.fill(dims, value, name=None)
* tf.constant(value, dtype=None, shape=None, name='Const')
## tf.zeros(shape, dtype=tf.float32, name=None)
该方法用于创建一个所有的值均为0的tensor对象．该操作返回的tensor对象中有如下属性：
>     1. 对象中所有的值均为0
>     2. 对象中所有的值的类型为dtype，如果dtype未指定，则默认为float32
>     3. 所创建的阶(通常也成为维度)由shape指定


* 参数:
  * shape: 待创建tensor对象的阶，该参数通常为一个int32类型数组，如[2], [3,4], [2,2,2]等，其阶(维度)分别表示１,2,3
  * dtype: 带创建tensor对象中各个元素的数据类型，必须为数字类型
  * name: 别名(可选)
* 返回:
  * 所有元素值均为0的tensor对象，阶和参数shape一样

常用调用方法：
````

````
## tf.zeros_like(tensor, dtype=None, name=None) 
该方法用于从一个已知的tensor对象中拷贝一个所有的值均为0的tensor对象，生成的tensor对象的阶(维度)和原始的一致．该操作返回的tensor对象中有如下属性：
>     1. 生成对象中所有的值均为0
>     2. 生成对象中所有的值的类型为dtype，如果dtype未指定，则默认为float32
>     3. 所创建的阶(通常也成为维度)和tensor的阶一致


* 参数:
  * tensor: 待拷贝的tensor对象(仅仅拷贝该对象的阶和对象中的元素类型，不拷贝元素数值)
  * dtype: 带创建tensor对象中各个元素的数据类型
    * 如果该类型未指定(为空)，则最终生成对象中的元素类型和参数tensor中的元素类型一致
    * 如果该类型已指定(不为空)，则该类型作为最终的使用类型
  * name: 别名(可选)
* 返回:
  * 所有元素值均为0的tensor对象，阶和参数shape一样

常用调用方法：
````

````
## tf.ones(shape, dtype=tf.float32, name=None) 
该方法用于创建一个所有的值均为1的tensor对象．该操作返回的tensor对象中有如下属性：
>     1. 生成对象中所有的值均为1
>     2. 生成对象中所有的值的类型为dtype，如果dtype未指定，则默认为float32
>     3. 所创建的阶(通常也成为维度)由shape指定


* 参数:
  * shape: 待创建tensor对象的阶，该参数通常为一个int32类型数组，如[2], [3,4], [2,2,2]等，其阶(维度)分别表示１,2,3
  * dtype: 带创建tensor对象中各个元素的数据类型，必须为数字类型
  * name: 别名(可选)
* 返回:
  * 所有元素值均为1的tensor对象，阶和参数shape一样

常用调用方法：
````

````
## tf.ones_like(tensor, dtype=None, name=None) 
该方法用于从一个已知的tensor对象中拷贝一个所有的值均为1的tensor对象，生成的tensor对象的阶(维度)和原始的一致．该操作返回的tensor对象中有如下属性：
>     1. 生成对象中所有的值均为1
>     2. 生成对象中所有的值的类型为dtype，如果dtype未指定，则默认为float32
>     3. 所创建的阶(通常也成为维度)和参数tensor的阶一致


* 参数:
  * tensor: 待拷贝的tensor对象(仅仅拷贝该对象的阶和对象中的元素类型，不拷贝元素数值)
  * dtype: 带创建tensor对象中各个元素的数据类型
    * 如果该类型未指定(为空)，则最终生成对象中的元素类型和参数tensor中的元素类型一致
    * 如果该类型已指定(不为空)，则该类型作为最终的使用类型
  * name: 别名(可选)
* 返回:
  * 所有元素值均为1的tensor对象，阶和参数tensor一致

常用调用方法：
````

````
## tf.fill(dims, value, name=None) 
该方法用于创建一个所有的值均为value的tensor对象．该操作返回的tensor对象中有如下属性：
>     1. 生成对象中所有的值均为value
>     2. 生成对象中所有的值的数据类型和value的数据类型一致
>     3. 所创建的阶(通常也成为维度)和dims一致


* 参数:
  * dims: 待创建tensor对象的阶，通常为一个int32类型的数组，如[2], [2, 2], [2, 2, 2]
  * value: 带创建tensor对象中各个元素的值，可为string, int, float, double等类型
  * name: 别名(可选)
* 返回:
  * 所有元素值均为value的tensor对象，其中元素的类型也和value一致，阶和参数dims一致

常用调用方法：
````

````
## tf.constant(value, dtype=None, shape=None, name='Const')
该方法用于创建一个值为value的tensor对象．．该操作返回的tensor对象中有如下属性：
>     1. 生成对象中所有的值均为value
>     2. 生成对象中所有的值的数据类型和dtype一致，如果dtype未指定，则和value一致
>     3. 所创建的阶由shape指定
参数"value"可以是一个常量值(字符串，数字等)，也可是一个类型为"dtype"的列表，
如果value是一个列表，并且参数shape也指定，那么该列表的长度必须小于等于阶shape中包含(暗示)的总的元素数量，一旦value的长度小于shape中指定的元素数量，那么最终生成的tensor对象中剩余的未显示设置值的元素均会被设置成value的最后一个属性的值
参数shape是可选的，如果该参数已指定，它表明了最终生成的tensor对象的阶，如果没有指定，那么最终生成的tensor对象可以是一个常量(0-D)或者一个一维数组(1-D)
如果参数dtype未指定，那么最终生成的tensor对象的元素类型和value的一致

* 参数:
  * value: 待创建tensor对象中各个元素的值或者数组等，如果dtype指定，则其类型必须和dtype相同
  * dtype: 带创建tensor对象中各个元素的类型
  * shape: 带创建tensor对象的阶(可选)
  * name: 别名(可选)
* 返回:
  * 所有元素值均为value的tensor对象
  
常用调用方法：
````

````