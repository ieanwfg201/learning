import tensorflow as tf
import example.data_structure.prints as p

sess = tf.InteractiveSession()

p.printLine("zeros(3)",tf.zeros([3], tf.int32))
p.printLine("zeros(3,4)",tf.zeros([3,4], tf.int32))
p.printLine("zeros(2,2,2)",tf.zeros([2,2,2], tf.int32))

t = [1,2,3]
p.printLineOrignal(t)
p.printLine("zeros_like(t)", tf.zeros_like(t))
t = [[1,2,3],[4,5,6]]
p.printLineOrignal(t)
p.printLine("zeros_like(t)", tf.zeros_like(t))

p.printLine("ones(3)",tf.ones([3], tf.int32))
p.printLine("ones(3,4)",tf.ones([3,4], tf.int32))
p.printLine("ones(2,2,2)",tf.ones([2,2,2], tf.int32))

t = [1,2,3]
p.printLineOrignal(t)
p.printLine("ones_like(t)", tf.ones_like(t))
t = [[1,2,3],[4,5,6]]
p.printLineOrignal(t)
p.printLine("ones_like(t)", tf.ones_like(t))


p.printLine("fills(8)", tf.fill([2,3], 8))

p.printLine("constant()", tf.constant([1,2,3,4,5,6,7,8]))
p.printLine("constant(-1)", tf.constant(-1, shape=[8]))
p.printLine("constant(10)", tf.constant(10, shape=[8],dtype=tf.float16))

p.printLine("linspace(0,10,1)", tf.linspace(0.0, 10.0, 1, name="linspace"))
p.printLine("linspace(0,10,3)", tf.linspace(0.0, 10.0, 3, name="linspace"))
p.printLine("linspace(0,10,5)", tf.linspace(0.0, 10.0, 5, name="linspace"))
p.printLine("linspace(0,10,7)", tf.linspace(0.0, 10.0, 7, name="linspace"))
p.printLine("linspace(0,10,10)", tf.linspace(0.0, 10.0, 10, name="linspace"))

p.printLine("range(0,10,1)", tf.range(0, 10, 2))
p.printLine("range(0,10,1)", tf.range(0, 10, 3))


# Create a tensor of shape [2, 3] consisting of random normal values, with mean
# -1 and standard deviation 4.
norm = tf.random_normal([2, 3], mean=-1, stddev=4)
p.printLine("random_normal()", tf.random_normal([2, 3], mean=-1, stddev=4))
p.printLine("random_normal()", tf.random_normal([2, 3], mean=-1, stddev=4))
# Shuffle the first dimension of a tensor
c = tf.constant([[1, 2], [3, 4], [5, 6]])
shuff = tf.random_shuffle(c)
p.printLine("random_shuffle()", tf.random_shuffle(c))
p.printLine("random_shuffle()", tf.random_shuffle(c))

# Set an op-level seed to generate repeatable sequences across sessions.
p.printLine("random_normal()", tf.random_normal([2, 3], seed=100))
p.printLine("random_normal()", tf.random_normal([2, 3], seed=1234))
p.printLineOrignal("Random sequence")
a = tf.random_uniform([1])
b = tf.random_normal([1])
with tf.Session() as sess1:
    p.printLineAll("A1-1", a, sess1)
    p.printLineAll("A1-1", a, sess1)
    p.printLineAll("B1-1", b, sess1)
    p.printLineAll("B1-1", b, sess1)

with tf.Session() as sess2:
    p.printLineAll("A1-2", a, sess2)
    p.printLineAll("A1-2", a, sess2)
    p.printLineAll("B1-2", b, sess2)
    p.printLineAll("B1-2", b, sess2)

p.printLineOrignal("Repeatable sequence")
# To generate the same repeatable sequence for an op across sessions, set the seed for the op:
a = tf.random_uniform([1], seed=1)
b = tf.random_normal([1])
with tf.Session() as sess1:
    p.printLineAll("A1-1", a, sess1)
    p.printLineAll("A1-1", a, sess1)
    p.printLineAll("B1-1", b, sess1)
    p.printLineAll("B1-1", b, sess1)

with tf.Session() as sess2:
    p.printLineAll("A1-2", a, sess2)
    p.printLineAll("A1-2", a, sess2)
    p.printLineAll("B1-2", b, sess2)
    p.printLineAll("B1-2", b, sess2)

p.printLineOrignal("Repeatable sequence - set_random_seed")
tf.set_random_seed(1234)
a = tf.random_uniform([1])
b = tf.random_normal([1])
with tf.Session() as sess1:
    p.printLineAll("A1-1", a, sess1)
    p.printLineAll("A1-1", a, sess1)
    p.printLineAll("B1-1", b, sess1)
    p.printLineAll("B1-1", b, sess1)

with tf.Session() as sess2:
    p.printLineAll("A1-2", a, sess2)
    p.printLineAll("A1-2", a, sess2)
    p.printLineAll("B1-2", b, sess2)
    p.printLineAll("B1-2", b, sess2)


sess.close
