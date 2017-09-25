import tensorflow as tf

def printOriginal(msg):
    print "# Original data: %(s)"%(msg)

def printValue(msg, key):
    print "# %s : %s - "%(msg, key)
    print key.eval()

def printValueInSession(msg, key, sess):
    print "# "+msg+": %s  -  value: "%(key)
    print sess.run(key)

def printRowValue(msg, key):
    print "# %s : %s"%(msg, key)

def printRaw(line):
    print "# %s"%(line)

def printHeader(line):
    print ""
    print "########### "+line+" ##########"
    print ""


if __name__ == '__main__':
    sess = tf.Session();
    key = "aaaa";
    # printValue("key", "value")