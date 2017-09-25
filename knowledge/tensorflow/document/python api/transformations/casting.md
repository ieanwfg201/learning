# Tensor Casting - Tensor对象转换
Tensor提供了一些可供在图(Graph)中对tensor数组做类型转换的方法
* tf.string_to_number(string_tensor, out_type=None, name=None)
* tf.to_double(x, name='ToDouble')
* tf.to_float(x, name='ToFloat')
* tf.to_bfloat16(x, name='ToBFloat16')
* tf.to_int32(x, name='ToInt32')
* tf.to_int64(x, name='ToInt64')
* tf.cast(x, dtype, name=None)

## tf.string_to_number(string_tensor, out_type=None, name=None)
将一个string类型的tensor转换成一个数字类型number类型的tensor对象
需要注意的是：当为一个float类型的字符串的时候，其无法转换成int类型．例如如下的调用是错误的：
>     # 错误的调用
>     data = tf.string_to_number("1.0", tf.int32)

* 参数:
  * string_tensor: 待转换的string(必选)． string类型或者string类型的tensor对象
  * out_type: 转换后的类型(可选)．默认情况下为float32, 
  * name: 别名(可选)
* 返回
  * 类型为out_type的tensor对象，其阶同输入参数string_tensor

基本调用方法如下：

    # 基本调用
    data = tf.string_to_number("1")
    p.printValue(" tf.string_to_number(\"1\")", data)
    data = tf.string_to_number("1", out_type=tf.int32)
    p.printValue("tf.string_to_number(\"1\", out_type=tf.int32)", data)
    data = tf.string_to_number("1", out_type=tf.int32, name="casting")
    p.printValue("tf.string_to_number(\"1\", out_type=tf.int32, name=\"casting\")", data)
     
    # tensor对象
    orignal = tf.constant("1", dtype=tf.string)
    p.printValue("Original", orignal)
    data = tf.string_to_number(orignal)
    p.printValue("tf.string_to_number(orignal)", data)
     
    # 多维度
    # orignal = [["1.0", "2"], ["3","4.0"], ["5","6.0"]]
    orignal = tf.constant([["1.0", "2"], ["3","4.0"], ["5","6.0"]], dtype = tf.string)
    p.printValue("Original", orignal)
    data = tf.string_to_number(orignal)
    p.printValue("tf.string_to_number(orignal)", data)
     
调用结果:

    #  tf.string_to_number("1") : Tensor("StringToNumber:0", shape=(), dtype=float32) - 
    1.0
    # tf.string_to_number("1", out_type=tf.int32) : Tensor("StringToNumber_1:0", shape=(), dtype=int32) - 
    1
    # tf.string_to_number("1", out_type=tf.int32, name="casting") : Tensor("casting:0", shape=(), dtype=int32) - 
    1
    # Original : Tensor("Const:0", shape=(), dtype=string) - 
    1
    # tf.string_to_number(orignal) : Tensor("StringToNumber_2:0", shape=(), dtype=float32) - 
    1.0
    # Original : Tensor("Const_1:0", shape=(3, 2), dtype=string) - 
    [['1.0' '2']
     ['3' '4.0']
     ['5' '6.0']]
    # tf.string_to_number(orignal) : Tensor("StringToNumber_3:0", shape=(3, 2), dtype=float32) - 
    [[ 1.  2.]
     [ 3.  4.]
     [ 5.  6.]]


错误的调用方法：

    # 错误调用方法，只能从int -> float, 不能从float -> int
    #data = tf.string_to_number("1.0 ", tf.int32) # wrong
    #data = tf.string_to_number("1 ", tf.float32) # right
    

## tf.to_double(x, name='ToDouble')
将一个tensor对象转换成double类型

* 参数:
  * x: 待转换的tensor/SparseTensor(必选)，可为数字
  * name: 别名(可选)
* 返回
  * 类型为double/float64的tensor对象，其阶同输入参数x
* 异常
  * TypeError: 当x不能被转换成double类型时
  
基本调用如下：

    ## 常用数字转换
    data = tf.to_double(1)
    p.printValue("", data)
    data = tf.to_double(1.0)
    p.printValue("", data)
    　
    # tensor对象转换
    data = tf.to_double(tf.constant("1", dtype=tf.int32))
    p.printValue("", data)
    
调用结果
    
    # tf.to_double(1 : Tensor("ToDouble:0", shape=(), dtype=float64) - 
    1.0
    # tf.to_double(1.0) : Tensor("ToDouble_1:0", shape=(), dtype=float64) - 
    1.0
    # tf.to_double(tf.constant(1, dtype=tf.int32)) : Tensor("ToDouble_2:0", shape=(), dtype=float64) - 
    1.0
    
错误调用方式：

    # 错误调用，不能直接使用string作为参数
    # data = tf.to_double("1.1") # wrong
    # data = tf.to_double(tf.constant("1.1", dtype=tf.string)) # wrong　该调用方式在执行data.eval()的时候报错: 
    # W tensorflow/core/framework/op_kernel.cc:890] Unimplemented: Cast string to double is not supported

## tf.to_float(x, name='ToFloat')
将一个tensor对象转换成float32类型

* 参数:
  * x: 待转换的tensor/SparseTensor(必选)，可为数字
  * name: 别名(可选)
* 返回
  * 类型为float32的tensor对象，其阶同输入参数x
* 异常
  * TypeError: 当x不能被转换成float32类型时

基本调用如下：

    ## 常用数字转换
    data = tf.to_float(1)
    p.printValue("", data)
    data = tf.to_float(1.0)
    p.printValue("", data)
    　
    # tensor对象转换
    data = tf.to_float(tf.constant("1", dtype=tf.int32))
    p.printValue("", data)
    
调用结果
    
    # tf.to_float(1 : Tensor("ToDouble:0", shape=(), dtype=float64) - 
    1.0
    # tf.to_float(1.0) : Tensor("ToDouble_1:0", shape=(), dtype=float64) - 
    1.0
    # tf.to_float(tf.constant(1, dtype=tf.int32)) : Tensor("ToDouble_2:0", shape=(), dtype=float64) - 
    1.0
    
错误调用方式：

    # 错误调用，不能直接使用string作为参数
    # data = tf.to_float("1.1") # wrong
    # data = tf.to_float(tf.constant("1.1", dtype=tf.string)) # wrong　该调用方式在执行data.eval()的时候报错: 
    # W tensorflow/core/framework/op_kernel.cc:890] Unimplemented: Cast string to double is not supported


## tf.to_bfloat16(x, name='ToBFloat16')
将一个tensor对象转换成bfloat16类型

* 参数:
  * x: 待转换的tensor/SparseTensor(必选)，可为数字
  * name: 别名(可选)
* 返回
  * 类型为bfloat16的tensor对象，其阶同输入参数x
* 异常
  * TypeError: 当x不能被转换成bfloat16类型时
  
## tf.to_int32(x, name='ToInt32')
将一个tensor对象转换成int32类型

* 参数:
  * x: 待转换的tensor/SparseTensor(必选)，可为数字
  * name: 别名(可选)
* 返回
  * 类型为int32的tensor对象，其阶同输入参数x
* 异常
  * TypeError: 当x不能被转换成int32类型时

基本调用如下：

    ## 常用数字转换
    data = tf.to_int32(1)
    p.printValue("", data)
    data = tf.to_int32(1.0)
    p.printValue("", data)
    　
    # tensor对象转换
    data = tf.to_int32(tf.constant("1", dtype=tf.int32))
    p.printValue("", data)
    
调用结果
    
    # tf.to_int32(1 : Tensor("ToDouble:0", shape=(), dtype=float64) - 
    1.0
    # tf.to_int32(1.0) : Tensor("ToDouble_1:0", shape=(), dtype=float64) - 
    1.0
    # tf.to_int32(tf.constant(1, dtype=tf.int32)) : Tensor("ToDouble_2:0", shape=(), dtype=float64) - 
    1.0
    
错误调用方式：

    # 错误调用，不能直接使用string作为参数
    # data = tf.to_int32("1.1") # wrong
    # data = tf.to_int32(tf.constant("1.1", dtype=tf.string)) # wrong　该调用方式在执行data.eval()的时候报错: 
    # W tensorflow/core/framework/op_kernel.cc:890] Unimplemented: Cast string to int32 is not supported


## tf.to_int64(x, name='ToInt64')
将一个tensor对象转换成int64类型

* 参数:
  * x: 待转换的tensor/SparseTensor(必选)，可为数字
  * name: 别名(可选)
* 返回
  * 类型为int64的tensor对象，其阶同输入参数x
* 异常
  * TypeError: 当x不能被转换成int64类型时

基本调用如下：

    ## 常用数字转换
    data = tf.to_int64(1)
    p.printValue("", data)
    data = tf.to_int64(1.0)
    p.printValue("", data)
    　
    # tensor对象转换
    data = tf.to_int64(tf.constant("1", dtype=tf.int32))
    p.printValue("", data)
    
调用结果
    
    # tf.to_int64(1 : Tensor("ToDouble:0", shape=(), dtype=float64) - 
    1.0
    # tf.to_int64(1.0) : Tensor("ToDouble_1:0", shape=(), dtype=float64) - 
    1.0
    # tf.to_int64(tf.constant(1, dtype=tf.int32)) : Tensor("ToDouble_2:0", shape=(), dtype=float64) - 
    1.0
    
错误调用方式：

    # 错误调用，不能直接使用string作为参数
    # data = tf.to_int64("1.1") # wrong
    # data = tf.to_int64(tf.constant("1.1", dtype=tf.string)) # wrong　该调用方式在执行data.eval()的时候报错: 
    # W tensorflow/core/framework/op_kernel.cc:890] Unimplemented: Cast string to int32 is not supported
  
## tf.cast(x, dtype, name=None)
将一个tensor对象转换成指定的dtype类型，不能和string转换

* 参数:
  * x: 待转换的tensor/SparseTensor(必选)，可为数字
  * dtype: 待转换的数据类型
  * name: 别名(可选)
* 返回
  * 类型为dtype的tensor对象，其阶同输入参数x
* 异常
  * TypeError: 当x不能被转换成指定类型类型时

基本调用如下：
    
    data = tf.cast([1.1,2.2,3.3], dtype=tf.int32)
    p.printValue("", data)
    
    data = tf.cast([1.1,2.2,3.3], dtype=tf.float16)
    p.printValue("", data)
    
调用结果
    
    #  : Tensor("Cast:0", shape=(3,), dtype=int32) - 
    [1 2 3]
    #  : Tensor("Cast_1:0", shape=(3,), dtype=float16) - 
    [ 1.09960938  2.19921875  3.30078125]