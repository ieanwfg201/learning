
def printLine(msg, key, sess):
    print "# "+msg+": %s  -  value: %s"%(key, sess.run(key))

def printLine(msg, key):
    print "# "+msg+": %s  -  value: %s"%(key, key.eval())

def printLineOrignal(key):
    print "# OrignalData: %s "%(key)

def printLineRaw(line):
    print "# %s"%(line)

def printHeader(line):
    print ""
    print "########### "+line+" ##########"
    print ""

def printLineAll(msg, key, sess):
    print "# "+msg+": %s  -  value: %s"%(key, sess.run(key))