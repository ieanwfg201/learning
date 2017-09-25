import tensorflow as tf
import numpy as np

def printLine(msg, instance, sess):
    print "# "+msg+": %s  -  value: %s"%(instance, sess.run(instance))

# number
sess = tf.Session()
print "##### numbers & string convent"
print ""
i = "10"
printLine("string_to_number(float32)", tf.string_to_number(i, tf.float32), sess)
printLine("string_to_number(int32)", tf.string_to_number(i, tf.int32), sess)

i = [1,2,3,4]
printLine("to_double()", tf.to_double(i), sess)
printLine("to_float()", tf.to_float(i), sess)
#printLine("to_bfloat16()", tf.to_bfloat16(i), sess)
printLine("to_int32()", tf.to_int32(i), sess)
printLine("to_int64()", tf.to_int64(i), sess)
printLine("cast(int32)", tf.cast(i, tf.int32), sess)
printLine("cast(float64)", tf.cast(i, tf.float64), sess)
printLine("to_int32()", tf.to_int32(i), sess)
printLine("to_int32()", tf.to_int32(i), sess)
printLine("to_int32()", tf.to_int32(i), sess)

# shape &shaping
print ""
print "################ Shape & Shaping"
print ""
t = [[[1, 1, 1], [2, 2, 2]], [[3, 3, 3], [4, 4, 4]]]
printLine("shape()", tf.shape(t), sess)
printLine("reshape([2,2,3])", tf.reshape(t, [2,2,3]), sess)
printLine("size()", tf.size(t), sess)
printLine("rank()", tf.rank(t), sess)
t = [1,2,3,4,5,6,7,8,9]
printLine("reshape([3,3])", tf.reshape(t, [3,3]), sess)
t = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]
d = tf.reshape(t, [1,2,2,4])
printLine("squeeze(1)-Before", d, sess)
printLine("squeeze(1)-After", tf.squeeze(d), sess)
#printLine("squeeze(2)", tf.squeeze(t, [-1]), sess)
printLine("expand_dim()-Before", d, sess)
printLine("expand_dim(d,0)", tf.expand_dims(d,0), sess)
printLine("expand_dim(d,1)", tf.expand_dims(d,1), sess)
printLine("expand_dim(d,-1)", tf.expand_dims(d,-1), sess)

print ""
print "################ Slicing and Joining"
print ""
t = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]
d = tf.reshape(t, [4,4])
printLine("Original data ", d,sess)
printLine("Slice([3,1])", tf.slice(d, [0,0], [3,1]), sess)
printLine("Slice([3,3])", tf.slice(d, [0,0], [3,3]), sess)

printLine("Original data ", d,sess)
# split 4*4 into two 2*4, by line
split0,split1 = tf.split(0,2, d)
printLine("split([0-0])", split0, sess)
printLine("split([0-1])", split1, sess)
# split 4*4 into two 2*4, by row
split0,split1 = tf.split(1,2, d)
printLine("split([1-0])", split0, sess)
printLine("split([1-1])", split1, sess)

i = [1,2,3,4]
d = tf.reshape(i,[2,2])
printLine("Original data ", d,sess)
printLine("tile(2,1)", tf.tile(d, [2,1]), sess)
printLine("tile(1,2)", tf.tile(d, [1,2]), sess)
printLine("tile(2,2)", tf.tile(d, [2,2]), sess)

d = tf.reshape(i,[2,2])
printLine("Original data ", d, sess)
printLine("padding([1,2])", tf.pad(d,[[1,1],[2,2]]), sess)
printLine("padding([1,0])", tf.pad(d,[[1,1],[0,0]]), sess)

t1 = [[1, 2, 3], [4, 5, 6]]
t2 = [[7, 8, 9], [10, 11, 12]]

printLine("concat(0)", tf.concat(0, [t1,t2]) ,sess)
printLine("concat(1)", tf.concat(1, [t1,t2]) ,sess)

#t = [1,2,3,4]
t = [[1,2],[3,4]]
printLine("pack()",tf.pack(t), sess)
printLine("unpack()",tf.unpack(tf.pack(t)), sess)

t = [1,2,3,4,5,6,7,8,9]
d = tf.reshape(t, [3,3])
printLine("Original data", d, sess)
printLine("reverse(true,false)", tf.reverse(d, [True,False]), sess)
printLine("reverse(false,True)", tf.reverse(d, [False,True]), sess)
printLine("reverse(true,True)", tf.reverse(d, [True,True]), sess)

t = [1,2,3,4,5,6,7,8,9,10,11,12]
d= tf.reshape(t, [3,4])
printLine("Original data", d, sess)
printLine("transpose()", tf.transpose(d), sess)
printLine("transpose()", tf.transpose(d, perm=[0,1]), sess)
printLine("transpose()", tf.transpose(d, perm=[1,0]), sess)

printLine("Gather()", tf.gather(t, [5,4,3,2,1]), sess)

#printLine("dynamic_partition()", tf.dynamic_partition(t, [7],2), sess)
#printLine("dynamic_stitch()", tf.dynamic_stitch(t, [7],2), sess)
if __name__ == '__main__':
    print
