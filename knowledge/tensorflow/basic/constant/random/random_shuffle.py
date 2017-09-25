#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()

# 必须为tensor对象
data = tf.constant([1,2,3,4,5], shape=[5])
p.printValue("random_shuffle_1",tf.random_shuffle(data))
data = tf.constant([0,1,2,3,4,5,6,7,8,9],shape=[5,2])
p.printValue("random_shuffle_1",tf.random_shuffle(data))


data = tf.constant([1,2,3,4,5], shape=[5])
p.printValue("Original", data)
p.printValue("random_shuffle_1",tf.random_shuffle(data))
p.printValue("random_shuffle_2",tf.random_shuffle(data))
p.printValue("random_shuffle_3",tf.random_shuffle(data))

data = tf.constant([0,1,2,3,4,5,6,7,8,9],shape=[5,2])
p.printValue("Original", data)
p.printValue("random_shuffle_1",tf.random_shuffle(data))
p.printValue("random_shuffle_2",tf.random_shuffle(data))
p.printValue("random_shuffle_3",tf.random_shuffle(data))

sess.close()