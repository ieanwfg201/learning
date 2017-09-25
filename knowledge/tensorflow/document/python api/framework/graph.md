# class tf.Graph
Graph是用于tensorflow计算，表现为数据流的图

一个Graph包含多个Operation对象(Operation对象是用于表示计算单元的，tensor对象是operation之间的数据单元)

默认情况下，graph对象都会被默认注册的，并且可以通过方法tf.get_default_graph()获取．如果需要将一个operation
添加到默认的graph中，简单调用一个operation即可，如：

    c = tf.constant(4.0)
    assert c.graph is tf.get_default_graph()
    
另外一种常用使用方法是使用context manager(Graph.as_default())，该方法会覆盖context中默认的graph为当前的图对象，
该操作是全局的，生命周期为lifetime．

    g = tf.Graph()
    with g.as_default():
      # Define operations and tensors in `g`.
      c = tf.constant(30.0)
      assert c.graph is g
      
注意：该类构造方法并不是线程安全的．所有的operation应该在同一个线程中创建，或者提供额外的同步操作(synchronization)
保证线程安全．除非特别表明，该类所有的方法均非线程安全．

## tf.Graph.__init__()
创建一个的新的，空的graph

## tf.Graph.as_default()
返回一个context manager并将当前graph作为默认的graph

当你想在同一个线程中使用不同的graph的时候，可以是用该方法．为方便使用，一个全局的默认graph会被创建，所有的operation
均会被添加到这个graph中(前提为没有明确的创建一个新的graph)．当用with关键字使用该方法的时候，表示所有的operation均会
在该图中操作

    # 1. Using Graph.as_default():
    g = tf.Graph()
    with g.as_default():
      c = tf.constant(5.0)
      assert c.graph is g
    
    # 2. Constructing and making default:
    with tf.Graph().as_default() as g:
      c = tf.constant(5.0)
      assert c.graph is g

* 返回
  * 返回一个以当前图作为默认图的context manager

## tf.Graph.as_graph_def(from_version=None)
返回一个表示该图的序列化的GraphDef对象

该序列化对象GraphDef可以在其他的图中被重新导入，导入方法为：import_graph_def()或者c++ api

该方法线程安全

* 参数:
  * from_version: 可选．如果该参数设置，则返回一个graph中version参数包含该值的GraphDef对象
* 返回:
  * 一个GraphDef对象(protocol buffer)

## tf.Graph.finalize()
finalize该图，使其为只读

当调用该方法后，任何其他的operation均不能添加到该图中．当需要将该图共享给其他的线程的时候，该方法可以保证
不会有额外的operation会被添加到该graph中，比如当使用QueueRunner的时候

## tf.Graph.finalized
当该graph已经finalized的时候(调用tf.Graph.finalize()方法)，返回true

## tf.Graph.control_dependencies(control_inputs)
返回一个指定控制依赖的context manager

使用with关键字来指定当前context中所有已经构造的operation应该需要被作为控制依赖的参数，例如

    with g.control_dependencies([a, b, c]):
      # `d` and `e` will only run after `a`, `b`, and `c` have executed.
      d = ...
      e = ...
      
control_dependencies()可以被嵌套使用，在这种情况下，一个新的Operation会对control_inputs集合拥有control dependency

    with g.control_dependencies([a, b]):
      # Ops declared here run after `a` and `b`.
      with g.control_dependencies([c, d]):
        # Ops declared here run after `a`, `b`, `c`, and `d`.
        
控制依赖仅仅请求当前context中的相关operation．一般情况下，当使用一个operation或者tensor的时候，使用控制依赖是毫无意义的
．如下例子说明该情况

    # WRONG
    def my_func(pred, tensor):
      t = tf.matmul(tensor, tensor)
      with tf.control_dependencies([pred]):
        # The matmul op is created outside the context, so no control
        # dependency will be added.
        return t
     
    # RIGHT
    def my_func(pred, tensor):
      with tf.control_dependencies([pred]):
        # The matmul op is created in the context, so a control dependency
        # will be added.
        return tf.matmul(tensor, tensor)
* 参数
  * control_inputs: 一个operation或者tensor对象列表．这些对象必须在运行前被执行或运算
* 返回
  * 本context下的指定了控制依赖的context manager
* 异常
  * TypeError: 当control_inputs并非一个operation或者tensor列表时
  
## tf.Graph.device(device_name_or_function)
返回一个指定了默认设备的context manager

参数device_name_or_function可能为一个设备名称字符创，也可以为设备的相关函数以及None
1. 如果为设备名称字符串：当前context中所有已构造的operation会被分派到该名称下的设备．
2. 如果为一个函数：它会当成一个函数对待，该函数为一个从operation到设备名称的映射．每次当一个
operation引入的时候，都会执行下该函数，该operation会被分派到该函数返回的设备名称下．
3. 如果为None：默认的device会被清除

例子：

    with g.device('/gpu:0'):
      # All operations constructed in this context will be placed
      # on GPU 0.
      with g.device(None):
        # All operations constructed in this context will have no
        # assigned device.
     
    # Defines a function from `Operation` to device string.
    def matmul_on_gpu(n):
      if n.type == "MatMul":
        return "/gpu:0"
      else:
        return "/cpu:0"
     
    with g.device(matmul_on_gpu):
      # All operations of type "MatMul" constructed in this context
      # will be placed on GPU 0; all other operations will be placed
      # on CPU 0.

* 参数
  * device_name_or_function: 待使用的设备名或者函数
* 返回
  * 返回一个指定了默认的设备的context manager

## tf.Graph.name_scope(name)
返回一个为operation创建了层次结构的context manager

一个graph中管理了名称范围栈．with name_scope(...)表达式会往该context中推一个新的名称

参数name可以为如下几种情况
1. 字符串(不以"/"结尾)会创建一个新的scope，该参数name会被附加到所有已创建的operation之前．如果name在这之前已经被使用过了
，那么会通过方法self.unique_name(name)使其唯一
2. 使用表达式with g.name_scope(...) as scope获取到之前的scope会被确定为一个绝对的name scope，通过这个方法，你可以重新
进入已经存在的scope
3. 值None或者空字符串会重置当前name scope为顶级(top-level, empty)name scope.

例如：

    with tf.Graph().as_default() as g:
      c = tf.constant(5.0, name="c")
      assert c_1.name == "c"
      c_1 = tf.constant(6.0, name="c")
      assert c_1.name == "c_1"
     
      # Creates a scope called "nested"
      with g.name_scope("nested") as scope:
        nested_c = tf.constant(10.0, name="c")
        assert nested_c.name == "nested/c"
     
        # Creates a nested scope called "inner".
        with g.name_scope("inner"):
          nested_inner_c = tf.constant(20.0, name="c")
          assert nested_inner_c.name == "nested/inner/c"
     
        # Create a nested scope called "inner_1".
        with g.name_scope("inner"):
          nested_inner_1_c = tf.constant(30.0, name="c")
          assert nested_inner_1_c.name == "nested/inner_1/c"
     
          # Treats `scope` as an absolute name scope, and
          # switches to the "nested/" scope.
          with g.name_scope(scope):
            nested_d = tf.constant(40.0, name="d")
            assert nested_d.name == "nested/d"
     
            with g.name_scope(""):
              e = tf.constant(50.0, name="e")
              assert e.name == "e"

scope本身的名称可以被方法获取到：with g.name_scope(...) as scope．该参数保存了所有的scope名称，例如：

    inputs = tf.constant(...)
    with g.name_scope('my_layer') as scope:
      weights = tf.Variable(..., name="weights")
      biases = tf.Variable(..., name="biases")
      affine = tf.matmul(inputs, weights) + biases
      output = tf.nn.relu(affine, name=scope)

* 参数
  * name: scope名称
* 返回
  * 注册了name的context manager

Graph实例支持任意数量的以名称为标示的集合．为方便使用，当建立一个很大的graph的时候，集合可以被批量存储，
例如：tf.Variable为当前graph中所创建的variable使用一个集合(名称为tf.GraphKeys.VARIABLES)．同时，调用者
也可能显示的调用其他的集合用于指定名称

## tf.Graph.add_to_collection(name, value)
将value以name为标示存储到集合中

* 参数
  * name: 集合中的key．例如：GraphKeys中包含了很多集合中标准的名称
  * value: 集合中的value

## tf.Graph.get_collection(name, scope=None)




