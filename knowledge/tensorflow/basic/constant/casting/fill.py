#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()

dim = [2,3]
data = tf.fill(dim, 5)
p.printValue("tf.fill(dim, value)", data)
data = tf.fill(dim, 5.0)
p.printValue("tf.fill(dim, value)", data)
data = tf.fill(dim, "5.0")
p.printValue("tf.fill(dim, value)", data)

sess.close()