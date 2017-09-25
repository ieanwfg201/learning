#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
data = tf.shape([[[1, 1, 1], [2, 2, 2]], [[3, 3, 3], [4, 4, 4]]])
p.printValue("tf.shape([[[1, 1, 1], [2, 2, 2]], [[3, 3, 3], [4, 4, 4]]])", data)
data = tf.shape([1,2,3,4,5,6])
p.printValue("tf.shape([1,2,3,4,5,6])", data)
data = tf.shape(1)
p.printValue("tf.shape(1)", data)

sess.close()