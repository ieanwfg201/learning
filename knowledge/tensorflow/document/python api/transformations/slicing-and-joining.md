# Slicing and Joining - 分割和合并
tensorFlow提供一些方法用于提取tensor中部分以及将多个tensor对象连接在一起
* tf.slice(input_, begin, size, name=None)
* tf.split(split_dim, num_split, value, name='split')
* tf.tile(input, multiples, name=None)
* tf.pad(input, paddings, name=None)
* tf.concat(concat_dim, values, name='concat')
* tf.pack(values, name='pack')
* tf.unpack(value, num=None, name='unpack')
* tf.reverse_sequence(input, seq_lengths, seq_dim, name=None)
* tf.reverse(tensor, dims, name=None)
* tf.transpose(a, perm=None, name='transpose')
* tf.gather(params, indices, name=None)
* tf.dynamic_partition(data, partitions, num_partitions, name=None)
* tf.dynamic_stitch(indices, data, name=None)


## tf.slice(input_, begin, size, name=None)
从input中提取部分出来

该操作会提取一个切片出来，起始位置为begin．size参数为shape，size[i]表示为input参数中想提取的第i个维度．起始位置start可以理解为input参数中的每一个维度的偏移量(从0开始)，size[i]表示input参数中第i个维度的起始位置(从1开始)

参数begin从0开始，size从1开始．如果size[i]为-1，该维度的所有数据均会保留下．关于begin和size需要满足如下公式

    size[i] = input.dim_size(i) - begin[i]
以及

    0 <= begin[i] <= begin[i] + size[i] <= Di for i in [0, n]
* 参数:
  * input: tensor对象
  * begin: int32或者int64的tensor对象，起始节点
  * size: int32或者int64的tensor对象，截止节点
  * name: 别名(可选)
* 返回
  * tensor对象，类型同input

基本使用例子
    
    original = tf.constant([1,2,3,4,5,6,7,8,9,10,11,12], shape=[2,2,3])
    p.printValue("Original value", original);
    data = tf.slice(original, [0,0,0],[1,1,1])
    p.printValue("tf.slice(original, [0,0,0],[1,1,1])", data);
     
    data = tf.slice(original, [1,1,1],[1,1,1])
    p.printValue("tf.slice(original, [1,1,1],[1,1,1])", data);
     
    data = tf.slice(original, [0,0,0],[2,1,1])
    p.printValue("tf.slice(original, [0,0,0],[2,1,1])", data);
     
    data = tf.slice(original, [0,0,0],[2,1,3])
    p.printValue("tf.slice(original, [0,0,0],[2,1,3])", data);
     
    data = tf.slice(original, [0,0,0],[1,2,1])
    p.printValue("tf.slice(original, [0,0,0],[1,2,1])", data);
     
    data = tf.slice(original, [0,0,0],[1,1,3])
    p.printValue("tf.slice(original, [0,0,0],[1,1,3])", data);
     
    data = tf.slice(original, [0,0,0],[2,2,2])
    p.printValue("tf.slice(original, [0,0,0],[2,2,2])", data);
    
输出结果：
    
    # Original value : Tensor("Const:0", shape=(2, 2, 3), dtype=int32) - 
    [[[ 1  2  3]
      [ 4  5  6]]
    
     [[ 7  8  9]
      [10 11 12]]]
    # tf.slice(original, [0,0,0],[1,1,1]) : Tensor("Slice:0", shape=(1, 1, 1), dtype=int32) - 
    [[[1]]]
    # tf.slice(original, [1,1,1],[1,1,1]) : Tensor("Slice_1:0", shape=(1, 1, 1), dtype=int32) - 
    [[[11]]]
    # tf.slice(original, [0,0,0],[2,1,1]) : Tensor("Slice_2:0", shape=(2, 1, 1), dtype=int32) - 
    [[[1]]
    
     [[7]]]
    # tf.slice(original, [0,0,0],[2,1,3]) : Tensor("Slice_3:0", shape=(2, 1, 3), dtype=int32) - 
    [[[1 2 3]]
    
     [[7 8 9]]]
    # tf.slice(original, [0,0,0],[1,2,1]) : Tensor("Slice_4:0", shape=(1, 2, 1), dtype=int32) - 
    [[[1]
      [4]]]
    # tf.slice(original, [0,0,0],[1,1,3]) : Tensor("Slice_5:0", shape=(1, 1, 3), dtype=int32) - 
    [[[1 2 3]]]
    # tf.slice(original, [0,0,0],[2,2,2]) : Tensor("Slice_6:0", shape=(2, 2, 2), dtype=int32) - 
    [[[ 1  2]
      [ 4  5]]
    
     [[ 7  8]
      [10 11]]]
    

## tf.split(split_dim, num_split, value, name='split')
将tensor对象value按照一个维度split_dim划分成num_split个tensor对象

其中num_split能够被value.shape[split_dim]完全整除，即：

    value.shape[split_dim] % num_split = 0

使用例子：

    # 'value' is a tensor with shape [5, 30]
    # Split 'value' into 3 tensors along dimension 1
    split0, split1, split2 = tf.split(1, 3, value)
    tf.shape(split0) ==> [5, 10]

* 参数:
  * split_dim: int32类型tensor．按照哪个维度做拆分，其值必须满足：[0, rank(value))
  * num_split: int32类型tensor．最终拆分成多少个对象
  * value: 待拆分的tensor对象
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    original = tf.constant([1,2,3,4,5,6,7,8,9,10,11,12], shape=[2,6])
    p.printValue("Original", original);
    　
    part1,part2=tf.split(0,2,original)
    p.printValue("-part1", part1)
    p.printValue("-part2", part2)
    　
    part1,part2,part3=tf.split(1,3,original)
    p.printValue("-part1", part1)
    p.printValue("-part2", part2)
    p.printValue("-part3", part3)
    
返回结果：
    
    # Original : Tensor("Const:0", shape=(2, 6), dtype=int32) - 
    [[ 1  2  3  4  5  6]
     [ 7  8  9 10 11 12]]
    # -part1 : Tensor("split:0", shape=(1, 6), dtype=int32) - 
    [[1 2 3 4 5 6]]
    # -part2 : Tensor("split:1", shape=(1, 6), dtype=int32) - 
    [[ 7  8  9 10 11 12]]
    # -part1 : Tensor("split_1:0", shape=(2, 2), dtype=int32) - 
    [[1 2]
     [7 8]]
    # -part2 : Tensor("split_1:1", shape=(2, 2), dtype=int32) - 
    [[ 3  4]
     [ 9 10]]
    # -part3 : Tensor("split_1:2", shape=(2, 2), dtype=int32) - 
    [[ 5  6]
     [11 12]]
     
## tf.tile(input, multiples, name=None)
通过覆盖一个对象来构造一个新的tensor对象

该操作通过不段的复制input对象multiples次后生成一个的新的tensor对象．结果值的第i个维度应该包含input.dims(i) * multiples[i]个元素，
并且input的值被重复了multiples[i]次，例如

    tiling [a b c d] by [2] produces [a b c d a b c d]
    tile([a,b,c,d], [2]) ===> [a,b,c,d,a,b,c,d]

* 参数:
  * input: tensor对象
  * multiples: int32数组类型tensor，其长度必须和input的维度一致
  * name: 别名(可选)
* 返回
  * tensor对象，类型同input

基本使用例子
    
    data = tf.tile([1,2,3,4], [2]);
    p.printValue("tf.tile([1,2,3,4], [2])", data)
     
    data = tf.tile([[1,2],[3,4]], [2,3])
    p.printValue("tf.tile([[1,2],[3,4]], [2,3])", data)
    
返回结果：
    
    # tf.tile([1,2,3,4], [2]) : Tensor("Tile:0", shape=(8,), dtype=int32) - 
    [1 2 3 4 1 2 3 4]
    # tf.tile([[1,2],[3,4]], [2,3]) : Tensor("Tile_1:0", shape=(4, 6), dtype=int32) - 
    [[1 2 1 2 1 2]
     [3 4 3 4 3 4]
     [1 2 1 2 1 2]
     [3 4 3 4 3 4]]
     
## tf.pad(input, paddings, name=None)
按照参数paddings用0填充input生成一个新的tensor对象

paddings参数是一个shape为[Dn, 2]的int32类型tensor(其中n表示参数input的阶)．对于输入参数中的每一个维度D，
paddings[D, 0]表示需要在input参数的当前维度前输入多少个0，
paddings[D, 1]表示需要在input参数的当前维度后输入多少个0

每个维度D最终的输出size为：

    paddings(D, 0) + input.dim_size(D) + paddings(D, 1)

* 参数:
  * input: tensor对象
  * paddings: int32的tensor对象
  * name: 别名(可选)
* 返回
  * 与input类型相同的paded的tensor对象

基本使用例子
    
    data = tf.pad([[1,1],[2,2]], [[1,2],[1,2]])
    p.printValue("", data)
     
    data = tf.pad([[1,2,3],[4,5,6]], [[1,2],[3,4]])
    p.printValue("", data)
    
调用结果：
    
    #  : Tensor("Pad:0", shape=(5, 5), dtype=int32) - 
    [[0 0 0 0 0]
     [0 1 1 0 0]
     [0 2 2 0 0]
     [0 0 0 0 0]
     [0 0 0 0 0]]
    #  : Tensor("Pad_1:0", shape=(5, 10), dtype=int32) - 
    [[0 0 0 0 0 0 0 0 0 0]
     [0 0 0 1 2 3 0 0 0 0]
     [0 0 0 4 5 6 0 0 0 0]
     [0 0 0 0 0 0 0 0 0 0]
     [0 0 0 0 0 0 0 0 0 0]]
    
## tf.concat(concat_dim, values, name='concat')
依据某一个维度concat_dim将多个tensor对象values链接在一起，生成一个新的tensor

如果满足
    
    values[i].shape = [D0, D1, ... Dconcat_dim(i), ...Dn]
    
那么生成结果的shape应该满足

    [D0, D1, ... Rconcat_dim, ...Dn]
    Rconcat_dim = sum(Dconcat_dim(i))
    
除了concat_dim的其他维度必须一致，并且每一个输入的参数数量必须一致
* 参数:
  * concat_dim: int32类型．需要做链接的维度序号
  * values: 多个tensor对象．
  * name: 别名(可选)
* 返回
  * tensor对象

基本使用例子
    
    t1 = tf.constant([[1,2,3],[4,5,6]])
    t2 = tf.constant([[1,2,3],[4,5,6]])
    data = tf.concat(0, [t1,t2])
    p.printValue("", data)
     
    data = tf.concat(1, [t1,t2])
    p.printValue("", data)
    
    # 一维为特例，即便数量不一致，也可做concat
    t1 = tf.constant([1,2,3])
    t2 = tf.constant([4,5])
    data = tf.concat(0, [t1,t2])
    p.printValue("", data)
    
调用结果：
    
    #  : Tensor("concat:0", shape=(4, 3), dtype=int32) - 
    [[1 2 3]
     [4 5 6]
     [1 2 3]
     [4 5 6]]
    #  : Tensor("concat_1:0", shape=(2, 6), dtype=int32) - 
    [[1 2 3 1 2 3]
     [4 5 6 4 5 6]]
    #  : Tensor("concat_2:0", shape=(5,), dtype=int32) - 
    [1 2 3 4 5]
    
错误结果：

    # 如果某一个维度元素数量不一致，则不能做concat
    t1 = tf.constant([[1,2,3,7],[4,5,6]])
    t2 = tf.constant([[1,2,3],[4,5,6,7]])
    # 如果维度不一致，则不能做concat
    t1 = tf.constant([[1,2,3],[4,5,6]])
    t2 = tf.constant([[1,2,3]])
    

## tf.pack(values, name='pack')
打包一些tensor对象为一个tensor对象，其维度+1(阶+1)

打包values为一个tensor对象，其最终结果满足：[len(values)] + values[0].shape，最终结果满足: output[i, ...] = values[i][...]

其和numpy的方法等同：
    
    tf.pack([x, y, z]) = np.asarray([x, y, z])

* 参数:
  * values: tensor对象列表
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    
错误调用：
    
    
## tf.unpack(value, num=None, name='unpack')
拆包tensor对象为多个tensor对象，其维度-1(阶-1)，和pack方法相反

其和numpy的方法等同：
    
    tf.unpack(x, n) = list(x)
* 参数:
  * value: 待拆包的tensor对象，其阶必须大于1
  * num: int32(可选)．默认为空，如果指定，则选择第一个维度
  * name: 别名(可选)
* 返回
  * tensor对象列表，拆包后的tensor对象
* 异常
  * ValueError: 当num未指定或者非法
基本使用例子
    
    
错误调用：
    
    
## tf.reverse_sequence(input, seq_lengths, seq_dim, name=None)
* 参数:
  * input: tensor对象
  * squeeze_dims: int32数组(可选)．默认为空，如果指定，则移除指定返回内的size为１的维度，维度的序列是从0开始
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    
错误调用：
    
    
## tf.reverse(tensor, dims, name=None)
* 参数:
  * input: tensor对象
  * squeeze_dims: int32数组(可选)．默认为空，如果指定，则移除指定返回内的size为１的维度，维度的序列是从0开始
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    
错误调用：
    
    
## tf.transpose(a, perm=None, name='transpose')
* 参数:
  * input: tensor对象
  * squeeze_dims: int32数组(可选)．默认为空，如果指定，则移除指定返回内的size为１的维度，维度的序列是从0开始
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    
错误调用：
    
    
## tf.gather(params, indices, name=None)
* 参数:
  * input: tensor对象
  * squeeze_dims: int32数组(可选)．默认为空，如果指定，则移除指定返回内的size为１的维度，维度的序列是从0开始
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    
错误调用：
    
    
## tf.dynamic_partition(data, partitions, num_partitions, name=None)
* 参数:
  * input: tensor对象
  * squeeze_dims: int32数组(可选)．默认为空，如果指定，则移除指定返回内的size为１的维度，维度的序列是从0开始
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    
错误调用：
    
    
## tf.dynamic_stitch(indices, data, name=None)
* 参数:
  * input: tensor对象
  * squeeze_dims: int32数组(可选)．默认为空，如果指定，则移除指定返回内的size为１的维度，维度的序列是从0开始
  * name: 别名(可选)
* 返回
  * tensor对象，类型和数值同input参数，但是shape不一样，生成新的tensor对中中shape为１的全部移除

基本使用例子
    
    
错误调用：
    
    