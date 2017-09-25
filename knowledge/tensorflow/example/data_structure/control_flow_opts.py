import tensorflow as tf
import example.data_structure.prints as p

# interactive session
sess = tf.InteractiveSession()

i = [1,2,3,4,5,6]
# Control Flow Operations
p.printHeader("Control Flow Operations")
d = tf.reshape(i, [2,3])
p.printLine("Orignal data", d)
p.printLine("identity(d)", tf.identity(d))
p.printLineRaw("identity(d) == d: "+str(tf.identity(d)==d))


#p.printLine("count_up_to", tf.count_up_to(d, 1))

p.printHeader("Logical Operators")
a = [True,False]
b = [False,True]
p.printLineOrignal("a: "+ str(a))
p.printLineOrignal("b: "+ str(b))
p.printLine("logical_and(a,b)", tf.logical_and(a,b))
p.printLine("logical_or(a,b)", tf.logical_or(a,b))
p.printLine("logical_not(a)", tf.logical_not(a))
p.printLine("logical_xor(a,b)", tf.logical_xor(a,b))

p.printHeader("Comparison Operators")
a = [0,1]
b = [1,0]
p.printLineOrignal("a: "+ str(a))
p.printLineOrignal("b: "+ str(b))
p.printLine("equal(a,b)", tf.equal(a,b))
p.printLine("not_equal(a,b)", tf.not_equal(a,b))
p.printLine("less(a,b)", tf.less(a,b))
p.printLine("less_equal(a,b)", tf.less_equal(a,b))
p.printLine("greater(a,b)", tf.greater(a,b))
p.printLine("greater_equal(a,b)", tf.greater_equal(a,b))

a = [1,2,3,4]
b = [5,6,7,8]
con = [True,False,True,False]
p.printLine("select(a,b)", tf.select(con,a,b))
a = [[True,False],[False,True]]
p.printLineOrignal("a: "+ str(a))
p.printLine("where(a)", tf.where(a))

p.printHeader("Debugging Operations")

p.printLine("is_finite(a)",tf.is_finite(1.0))
p.printLine("is_inf(a)",tf.is_inf(1.0))
p.printLine("is_nan(a)",tf.is_nan(1.0))

a = [1.0,2.0,3.0,4.0,5.0]
p.printLine("verify_tensor_all_finite(a)",tf.verify_tensor_all_finite(a, "error message"))
p.printLine("check_numerics(a)",tf.check_numerics(a, "error message"))
tf.add_check_numerics_ops()

sess.close