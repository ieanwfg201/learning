import tensorflow as tf
import example.data_structure.prints as prints;

a = tf.constant(5.0)
b = tf.constant(6.0)
c = a*b
sess = tf.Session()
prints.printLine("a*b", c, sess)

print sess.run(c)

with tf.Session() as sess:
    print sess.run(c)

sess.close

sess = tf.InteractiveSession()
a = tf.constant(5.0)
b = tf.constant(6.0)
c = a * b
# We can just use 'c.eval()' without passing 'sess'
print c.eval()
sess.close()


# ERROR class
