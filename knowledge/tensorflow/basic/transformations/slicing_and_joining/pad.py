#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()

data = tf.pad([[1,1],[2,2]], [[1,2],[1,2]])
p.printValue("", data)

data = tf.pad([[1,2,3],[4,5,6]], [[1,2],[3,4]])
p.printValue("", data)


sess.close()