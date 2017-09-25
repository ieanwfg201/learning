#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
# array
data = tf.constant([1,2,3,4,5])
p.printValue("", data)
# number
data = tf.constant(1)
p.printValue("", data)
# string
data = tf.constant("aaaa")
p.printValue("", data)
# boolean
data = tf.constant(True, dtype=tf.bool)
p.printValue("", data)

data = tf.constant([1,2,3,4,5], shape=[5])
p.printValue("", data)
data = tf.constant([1,2,3,4,5], shape=[8])
p.printValue("", data)
data = tf.constant([1,2,3,4,5], dtype=tf.double, shape=[8])
p.printValue("", data)

data = tf.constant(3, dtype=tf.double, shape=[8])
p.printValue("", data)
data = tf.constant([3], dtype=tf.double, shape=[8])
p.printValue("", data)

# wrong usage
# data = tf.constant([1,2,3,4,5], shape=[1])

sess.close()