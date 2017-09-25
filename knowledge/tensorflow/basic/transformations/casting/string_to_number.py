#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
# 基本调用
data = tf.string_to_number("1")
p.printValue(" tf.string_to_number(\"1\")", data)
data = tf.string_to_number("1", out_type=tf.int32)
p.printValue("tf.string_to_number(\"1\", out_type=tf.int32)", data)
data = tf.string_to_number("1", out_type=tf.int32, name="casting")
p.printValue("tf.string_to_number(\"1\", out_type=tf.int32, name=\"casting\")", data)

# tensor对象
orignal = tf.constant("1", dtype=tf.string)
p.printValue("Original", orignal)
data = tf.string_to_number(orignal)
p.printValue("tf.string_to_number(orignal)", data)

# 多维度
# orignal = [["1.0", "2"], ["3","4.0"], ["5","6.0"]]
orignal = tf.constant([["1.0", "2"], ["3","4.0"], ["5","6.0"]], dtype = tf.string)
p.printValue("Original", orignal)
data = tf.string_to_number(orignal)
p.printValue("tf.string_to_number(orignal)", data)


# 错误调用方法，只能从int -> float, 不能从float -> int
# data = tf.string_to_number("1.0 ", out_type=tf.int32) # wrong
# data = tf.string_to_number("1 ", out_type=tf.float32) # right


sess.close();