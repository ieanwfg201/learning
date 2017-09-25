#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
original = tf.constant([1,2,3,4,5,6,7,8,9,10,11,12], shape=[2,2,3])
p.printValue("Original value", original);
data = tf.slice(original, [0,0,0],[1,1,1])
p.printValue("tf.slice(original, [0,0,0],[1,1,1])", data);

data = tf.slice(original, [1,1,1],[1,1,1])
p.printValue("tf.slice(original, [1,1,1],[1,1,1])", data);

data = tf.slice(original, [0,0,0],[2,1,1])
p.printValue("tf.slice(original, [0,0,0],[2,1,1])", data);

data = tf.slice(original, [0,0,0],[2,1,3])
p.printValue("tf.slice(original, [0,0,0],[2,1,3])", data);

data = tf.slice(original, [0,0,0],[1,2,1])
p.printValue("tf.slice(original, [0,0,0],[1,2,1])", data);

data = tf.slice(original, [0,0,0],[1,1,3])
p.printValue("tf.slice(original, [0,0,0],[1,1,3])", data);

data = tf.slice(original, [0,0,0],[2,2,2])
p.printValue("tf.slice(original, [0,0,0],[2,2,2])", data);

sess.close()
