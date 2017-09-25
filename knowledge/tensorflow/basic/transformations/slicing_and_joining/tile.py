#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
data = tf.tile([1,2,3,4], [2]);
p.printValue("tf.tile([1,2,3,4], [2])", data)

data = tf.tile([[1,2],[3,4]], [2,3])
p.printValue("tf.tile([[1,2],[3,4]], [2,3])", data)

sess.close()