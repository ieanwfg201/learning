import tensorflow as tf

# Create a variable.
w = tf.Variable(100.0, name="w")
print w
print w.initialized_value()

b = 5
# The overloaded operators are available too.
z = tf.sigmoid(w + b)
print z
# Assign a new value to the variable with `assign()` or a related method.
w.assign(w + 1.0)
print w
w.assign_add(1.0)
print w


init_op = tf.initialize_all_variables()
with tf.Session() as sess:
    sess.run(init_op)
print tf.to_int64(w)

if __name__ == '__main__':
    print;