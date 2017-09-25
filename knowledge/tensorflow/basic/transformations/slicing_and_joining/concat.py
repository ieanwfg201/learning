#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
t1 = tf.constant([[1,2,3],[4,5,6]])
t2 = tf.constant([[1,2,3],[4,5,6]])
data = tf.concat(0, [t1,t2])
p.printValue("", data)

data = tf.concat(1, [t1,t2])
p.printValue("", data)

t1 = tf.constant([1,2,3])
t2 = tf.constant([4,5])
data = tf.concat(0, [t1,t2])
p.printValue("", data)

sess.close();