#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()

# 创建一个维度为１, 类型为int的对象
data = tf.ones([1], dtype=tf.int32)
p.printValue("sess.ones([1], dtype=tf.int32)", data)
# 创建一个维度为3, 类型为int的对象
data = tf.ones([1,2,1], dtype=tf.int32)
p.printValue("sess.ones([3,4,5], dtype=tf.int32)", data)
# double
data = tf.ones([8], dtype=tf.double)
p.printValue("sess.ones([1], dtype=tf.double)", data)
# float
data = tf.ones([8], dtype=tf.float16)
p.printValue("sess.ones([1], dtype=tf.float16)", data)

# [ERROR] sharp不能为int类型，需要制定为tensor sharp类型
# data = tf.ones(1, dtype=tf.int32)
# p.printValue("tf.ones(1, dtype=tf.int32)", data)

# 可是使用如下方法替换

data = tf.ones([1], dtype=tf.int32)
p.printValue("tf.ones([1], dtype=tf.int32)", data)
data = tf.ones([1], dtype=tf.int32)
p.printValue("tf.ones([1], dtype=tf.int32)", data)

sess.close()