#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()

print "#####  Graph-level-seed: NO, Op-level-sedd: NO"
a = tf.random_uniform([5])
with tf.Session() as sess1:
    print "Session 1 - random_uniform"
    print sess1.run(a)
    print sess1.run(a)

with tf.Session() as sess2:
    print "Session 2 - random_uniform"
    print sess2.run(a)
    print sess2.run(a)

print ""
print "#####  Graph-level-seed: NO, Op-level-sedd: YES"
a = tf.random_uniform([5], seed=1)
with tf.Session() as sess1:
    print "Session 1 - random_uniform"
    print sess1.run(a)
    print sess1.run(a)

with tf.Session() as sess2:
    print "Session 2 - random_uniform"
    print sess2.run(a)
    print sess2.run(a)


print ""
print "#####  Graph-level-seed: YES, Op-level-sedd: NO"
tf.set_random_seed(123)
a = tf.random_uniform([5])
with tf.Session() as sess1:
    print "Session 1 - random_uniform"
    print sess1.run(a)
    print sess1.run(a)

with tf.Session() as sess2:
    print "Session 2 - random_uniform"
    print sess2.run(a)
    print sess2.run(a)


print ""
print "#####  Graph-level-seed: YES, Op-level-sedd: YES"
tf.set_random_seed(123)
a = tf.random_uniform([5], seed=1)
with tf.Session() as sess1:
    print "Session 1 - random_uniform"
    print sess1.run(a)
    print sess1.run(a)

with tf.Session() as sess2:
    print "Session 2 - random_uniform"
    print sess2.run(a)
    print sess2.run(a)

sess.close()