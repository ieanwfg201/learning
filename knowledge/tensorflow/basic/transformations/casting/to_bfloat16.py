#coding=utf8
import tensorflow as tf
import basic.util.prints as p

sess = tf.InteractiveSession()
## 常用数字转换
# data = tf.to_bfloat16(1)
# p.printValue("tf.to_double(1", data)
# data = tf.to_bfloat16(1.0)
# p.printValue("tf.to_double(1.0)", data)

# tensor对象转换
data = tf.to_bfloat16(tf.constant(1.0, dtype=tf.bfloat16))
p.printValue("tf.to_double(tf.constant(1, dtype=tf.int32))", data)

# 错误调用，不能直接使用string作为参数
# data = tf.to_bfloat16("1.1") # wrong
# data = tf.to_bfloat16(tf.constant("1.1", dtype=tf.string)) # right


sess.close();