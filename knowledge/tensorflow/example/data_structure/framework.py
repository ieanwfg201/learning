import tensorflow as tf
import example.data_structure.prints as p

sess = tf.InteractiveSession()

c = tf.constant(4.0)
assert c.graph is tf.get_default_graph()

g = tf.Graph()
with g.as_default():
    # Define operations and tensors in `g`.
    c = tf.constant(30.0)
    assert c.graph is g

g = tf.Graph()
with g.as_default():
    c = tf.constant(5.0)
    assert c.graph is g

# 2. Constructing and making default:
with tf.Graph().as_default() as g:
    c = tf.constant(5.0)
    assert c.graph is g


sess.close