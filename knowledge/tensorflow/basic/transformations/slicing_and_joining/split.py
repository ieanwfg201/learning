#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
original = tf.constant([1,2,3,4,5,6,7,8,9,10,11,12], shape=[2,6])
p.printValue("Original", original);

part1,part2=tf.split(0,2,original)
p.printValue("-part1", part1)
p.printValue("-part2", part2)

part1,part2,part3=tf.split(1,3,original)
p.printValue("-part1", part1)
p.printValue("-part2", part2)
p.printValue("-part3", part3)

sess.close()