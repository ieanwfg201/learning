#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
data = tf.cast([1.1,2.2,3.3], dtype=tf.int32)
p.printValue("", data)

data = tf.cast([1.1,2.2,3.3], dtype=tf.float16)
p.printValue("", data)

sess.close()