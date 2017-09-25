# Shapes and Shaping - 模型/形状以及模型/形状转换
TensorFlow提供了一些方法用于设置tensor对象的模型/形状
* tf.shape(input, name=None)
* tf.size(input, name=None)
* tf.rank(input, name=None)
* tf.reshape(tensor, shape, name=None)
* tf.squeeze(input, squeeze_dims=None, name=None)
* tf.expand_dims(input, dim, name=None)

## tf.shape(input, name=None)
返回一个tensor对象的模型/形状，这个操作返回一个int数组，表明输入input的模型/形状

* 参数:
  * input: tensor对象
  * name: 别名(可选)
* 返回
  * 类型为int32的数组，表明input的模型/形状

基本使用例子
    
    data = tf.shape([[[1, 1, 1], [2, 2, 2]], [[3, 3, 3], [4, 4, 4]]])
    p.printValue("tf.shape([[[1, 1, 1], [2, 2, 2]], [[3, 3, 3], [4, 4, 4]]])", data)
    data = tf.shape([1,2,3,4,5,6])
    p.printValue("tf.shape([1,2,3,4,5,6])", data)
    data = tf.shape(1)
    p.printValue("tf.shape(1)", data)

返回结果:
    
    # tf.shape([[[1, 1, 1], [2, 2, 2]], [[3, 3, 3], [4, 4, 4]]]) : Tensor("Shape:0", shape=(3,), dtype=int32) - 
    [2 2 3]
    # tf.shape([1,2,3,4,5,6]) : Tensor("Shape_1:0", shape=(1,), dtype=int32) - 
    [6]
    # tf.shape(1) : Tensor("Shape_2:0", shape=(0,), dtype=int32) - 
    []
    
错误调用：
    

## tf.size(input, name=None)
返回tensor对象中的元素个数，该操作返回输入input对象中所有的元素个数

* 参数:
  * input: tensor对象
  * name: 别名(可选)
* 返回
  * 类型为int32的数值，表明input的元素个数

基本使用例子
    
    
错误调用：
    
    

## tf.rank(input, name=None)
返回tensor对象中的阶

**NOTE:** tensor对象的阶和矩阵的阶是不一样的，tensor对象的阶是一个数字，表明该对象的最深元素的层数，通常也成为张量(order), 维度(degree), ndims

* 参数:
  * input: tensor对象
  * name: 别名(可选)
* 返回
  * 类型为int32的数字，表明input的阶/张量/维度

基本使用例子
    
    
错误调用：
    
    

## tf.reshape(tensor, shape, name=None)
转换tensor对象的模型/形状为指定值shape．给定一个tensor对象，调用该方法后将返回一个值同该tensor但是shape为指定参数shape的新tensor对象

如果shape参数为-1，那么结果将返回一个flatten的tensor对象(数组)

如果shape参数为数组或者多维数组，那么结果返回一个值为参数值并且shape为参数shape的新tensor对象．在这种情况下，如果参数shape容纳的参数个数大小
比tensor对象中所有参数个数大小还要大，那么多余的值均为tensor参数的最后一个参数值

* 参数:
  * tensor: tensor对象
  * shape: int32类型的tensor对象，表示最终返回对象的shape
  * name: 别名(可选)
* 返回
  * tensor对象，其shape同参数shape

基本使用例子
    
    
错误调用：
    
    

## tf.squeeze(input, squeeze_dims=None, name=None)
移除掉输入参数shape为１的维度，并且append到之前的维度中

给定一个tensor对象input，该操作会返回一个类型相同，并且移除了所有维度值为１的维度的tensor对象．

如果不需要移除所有的size为１的维度，那么可以使用参数squeeze_dims来指定

* 参数:
  * input: tensor对象
  * squeeze_dims: int32数组(可选)．默认为空，如果指定，则移除指定返回内的size为１的维度，维度的序列是从0开始
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    
错误调用：
    
    

## tf.expand_dims(input, dim, name=None)
向tensor对象input的shape中添加一个size为１的维度．

给定一个tensor对象input，该操作会维度为dim的位置插入一个size为１的维度．维度的起始位置为0．如果dim为-1，则自动添加到最后维度．

当往一个元素中添加多个维度的时候，该方法非常好用．比如：如果你有一个图片其shape为[height, width, channels]，那么可以通过该方法expand_dims(image, 0)将其shape设置为[1, height, width, channels]，这样可以添加多个图片了．其中第一个元素表示的为第几个图片．

* 参数:
  * input: tensor对象
  * dim: int32类型常量，带插入的维度位置, 0表示在第一个位置, -1表示最后一个位置
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中dim位置新增一个维度，其size为１

基本使用例子
    
    
错误调用：
    
    
