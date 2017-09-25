# Session management - Session管理
* class tf.Session
* class tf.InteractiveSession
* tf.get_default_session()

## class tf.Session - Session类
该类是用于运行TensorFlow的操作(operation/op)的

Session对象中包含了一些运行环境，所有的操作(Operation)必须在该环境在运行，同时，也会引入相关tensor对象
    
    # Build a graph.
    a = tf.constant(5.0)
    b = tf.constant(6.0)
    c = a * b
     
    # Launch the graph in a session.
    sess = tf.Session()
     
    # Evaluate the tensor `c`.
    print sess.run(c)

一个session中可以包含资源(resources)，例如vairable,queues,readers之类．当这些资源不需要了的时候，需要手动去释放．可以通过两种方式实现自然的释放：
  1. 手动调用close()方法
  2. 将session作为一个context管理器(context manager)来使用．
  
    # Using the `close()` method.
    sess = tf.Session()
    sess.run(...)
    sess.close()
    
    # Using the context manager.
    with tf.Session() as sess:
      sess.run(...)
      
### tf.ConfigProto 
该方法通常用于创建session对象，用于对session进行参数配置，其参数包括:
>     log_device_placement=True : 是否打印设备分配日志
>     allow_soft_placement=True ： 如果你指定的设备不存在，允许TF自动分配设备

如下为基本使用方法:

    with tf.Session(config = tf.ConfigProto(...),...)
    tf.ConfigProto(log_device_placement=True,allow_soft_placement=True)

控制GPU资源使用率

    #allow growth
    config = tf.ConfigProto()
    config.gpu_options.allow_growth = True
    session = tf.Session(config=config, ...)
    # 使用allow_growth option，刚一开始分配少量的GPU容量，然后按需慢慢的增加，由于不会释放
    #内存，所以会导致碎片

    # per_process_gpu_memory_fraction
    gpu_options=tf.GPUOptions(per_process_gpu_memory_fraction=0.7)
    config=tf.ConfigProto(gpu_options=gpu_options)
    session = tf.Session(config=config, ...)
    #设置每个GPU应该拿出多少容量给进程使用，0.4代表 40%
    
控制使用哪块GPU

    ~/ CUDA_VISIBLE_DEVICES=0  python your.py#使用GPU0
    ~/ CUDA_VISIBLE_DEVICES=0,1 python your.py#使用GPU0,1
    #注意单词不要打错

### tf.Session.__init__(target='', graph=None, config=None)
创建一个TensorFlow session对象．

如果graph参数未指定，那么一个默认的graph会启动加载．如果在一个流程中创建了多个graph(使用tf.Graph()创建)，那么你必须在每一个Graph中使用不同的session，当然，同一个Graph可以使用多个session．
在这种情况下，可以将graph作为参数传入到session构造函数中．

* 参数:
  * target: 可选参数．所连接的执行引擎．默认为空时使用一个in-process引擎．当前只支持空字符串(即默认引擎)
  * graph: 可选参数．所包含的Graph
  * config: 可选参数．当前session的proto buffer配置参数．可通过tf.ConfigProto()来创建
* 返回:
  * session对象
  
调用方法：
````
````

### tf.Session.run(fetches, feed_dict=None)
    
运行操作(operations)并对tensor对象fetches求值
该方法是TensorFlow的运算操作．通过运行图片段(Graph fragment)来执行每个操作(operation)，并对每一个tensor对象求值，并替换feed_dict中的参数作为响应返回
fetches参数可能是多个或者单个graph节点，值不同将决定这个方法的返回值
单个graph节点时可能为如下类型中一个
  1. 如果fetches的第i个参数是operation，那么第i个返回值肯定为none
  2. 如果fetches的第i个参数是tensor，那么第i个返回值肯定为numpy的一个ndarray数组并包含这个tensor对象的值
  3. 如果fetches的第i个参数是SparseTensor，那么第i个返回值肯定为SparseTensorValue对象，包含该tensor值

可选参数feed_dict允许用户去覆盖当前Graph中的tensor对象值，feed_dict中key的值可以为如下类型：
  1. 如果key是一个tensor：其值可能是一个类型同原始的tensor对象类型一致的python数值，字符串，列表或者numpy的ndarray数组，额外的，如果key是一个预留值(placeholder)，将会检验这个值的阶是否满足这个预留值(placeholder)
  2. 如果key是一个SparseTensor：其值应该为SparseTensorValue对象

* 参数:
  * fetches: 一个graph节点，或者graph节点列表
  * feed_dict: 字典类型(dictionary)，映射当前graph的节点值
* 返回:
  * 当fetches是一个单个graph节点，单个值返回，如果为多个，则多个值返回 
* 异常:
  * RuntimeError: 当当前session是一个非法的状态的时候，比如已经关闭了
  * TypeError: 当fetches或feed_dict的key是非法类型的时候
  * ValueError: 当fetches或feed_dict的key是非法的或者关联到一个不存在的tensor对象时候
  
调用方法：
````
````


### tf.Session.close()
关闭当前session．调用该方法将释放当前session下所有关联的相关资源

* 异常:
  * RuntimeError: 当有错误发生时候会报该错误

### tf.Session.graph
当前session所关联的图(Graph)

### tf.Session.as_default()
返回context管理器(context manager)，并吧当前session作为默认session
使用关键字with来指定如Operation.run(...), Tensor.run(...)之类的请求应该在当前session执行．如：
    
    c = tf.constant(..)
    sess = tf.Session()
     
    with sess.as_default():
      assert tf.get_default_session() is sess
      print c.eval()

如果需要获取到当前默认的session，使用方法

    tf.get_default_session()
    
>    需要注明的是，当离开这个分支的时候，as_default()并不会自动关闭session，所以需要显示的调用sess.close()方法来释放相关资源
>    当然如果使用with命令创建的session，那么它会自动调用close方法来释放资源，即便发生异常

    c = tf.constant(...)
    sess = tf.Session()
    with sess.as_default():
      print c.eval()
    # ...
    with sess.as_default():
      print c.eval()
     
    sess.close()

默认的图(Graph)实际上为当前线程的一个属性．所以如果你创建了一个线程，并且在新创建的线程中调用该默认的session，那么必须在新创建的线程中添加with sess.as_default()．

* 返回:
  * 一个使用当前session作为默认session的context管理器(context manager)
  

## class tf.InteractiveSession - InteractiveSession类
该类是用于创建交互性上下文的Tensorflow session对象，例如shell

与普通的session对象相比，其唯一差别在于：InteractiveSession在构造的时候会将其作为默认的session(as_default())，所以方法Tensor.eval()和Operation.run()会默认使用这个session去执行

在交互性的环境下，以这种方式创建session是非常方便，这样避免了在参数中传入session对象

    sess = tf.InteractiveSession()
    a = tf.constant(5.0)
    b = tf.constant(6.0)
    c = a * b
    # We can just use 'c.eval()' without passing 'sess'
    print c.eval()
    sess.close()

需要注意的是，当调用了with关键字的时候，一个普通的session会注册自身成为默认的执行session．通常在非交互性的环境下，代码一般都是如下这样：

    a = tf.constant(5.0)
    b = tf.constant(6.0)
    c = a * b
    with tf.Session():
      # We can also use 'c.eval()' here.
      print c.eval()

### tf.InteractiveSession.__init__(target='', graph=None)
创建一个交互型的TensorFlow session对象．

如果graph参数未指定，那么一个默认的graph会启动加载．如果在一个流程中创建了多个graph(使用tf.Graph()创建)，那么你必须在每一个Graph中使用不同的session，当然，同一个Graph可以使用多个session．
在这种情况下，可以将graph作为参数传入到session构造函数中．

* 参数:
  * target: 可选参数．所连接的执行引擎．默认为空时使用一个in-process引擎．当前只支持空字符串(即默认引擎)
  * graph: 可选参数．所包含的Graph
* 返回:
  * interactiveSession对象
  
调用方法：
````
````

### tf.InteractiveSession.close()
关闭session对象

## tf.get_default_session()
为当前线程会犯一个默认的session．返回的session会是上一个通过session或session.as_default()所构造的上下文(context)

* 返回:
  * 为当前线程返回默认的session对象