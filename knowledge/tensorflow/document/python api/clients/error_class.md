# Error classes - Error类
* class tf.OpError
* class tf.errors.CancelledError
* class tf.errors.UnknownError
* class tf.errors.InvalidArgumentError
* class tf.errors.DeadlineExceededError
* class tf.errors.NotFoundError
* class tf.errors.AlreadyExistsError
* class tf.errors.PermissionDeniedError
* class tf.errors.UnauthenticatedError
* class tf.errors.ResourceExhaustedError
* class tf.errors.FailedPreconditionError
* class tf.errors.AbortedError
* class tf.errors.OutOfRangeError
* class tf.errors.UnimplementedError
* class tf.errors.InternalError
* class tf.errors.UnavailableError
* class tf.errors.DataLossError

## class tf.OpError
如果可知的操作(operation)失败的时候，报该异常
### tf.OpError.node_def
node_def中会表明是那个op导致的错误
### tf.OpError.__init__(node_def, op, message, error_code)

* 参数
  * node_def: 
  * op: 发生错误的操作(operation)，默认为none
  * message: 错误描述信息
  * error_code: 错误代码


## class tf.errors.CancelledError

## class tf.errors.UnknownError
## class tf.errors.InvalidArgumentError
## class tf.errors.DeadlineExceededError
## class tf.errors.NotFoundError
## class tf.errors.AlreadyExistsError
## class tf.errors.PermissionDeniedError
## class tf.errors.UnauthenticatedError
## class tf.errors.ResourceExhaustedError
## class tf.errors.FailedPreconditionError
## class tf.errors.AbortedError
## class tf.errors.OutOfRangeError
## class tf.errors.UnimplementedError
## class tf.errors.InternalError
## class tf.errors.UnavailableError
## class tf.errors.DataLossError