# Sequence - 序列
* tf.linspace(start, stop, num, name=None)
* tf.range(start, limit, delta=1, name='range')
## tf.linspace(start, stop, num, name=None)
生成一个平均分布的float类型数字列表，其特征为：
>     1. 生成数列以start开始，第一个元素为start
>     2. 生成数列以end结束，最后一个元素为end
>     3. 生成数列总长度为num
当num>0的时候，那么增量应该为：
>     increment = (stop-start)/(num-1)
* 参数:
  * start: 开始值，tensor对象．类型必须为float32, float64．最终生成数列的第一个元素的值
  * stop: 截止值，tensor对象．类型和start一致．最终生成数列的最后元素的值
  * num: 总数，tensor对象．类型为int32．生成数列的总的个数
  * name: 别名(可选)
* 返回:
  * tensor对象．长度为num，以start开始，end截止的数列
常用调用方法：
````

````
## tf.range(start, limit, delta=1, name='range')
生成一个平均分布的int类型数字列表，其特征为:
>     1. 生成数列以start开始，第一个元素为start
>     2. 生成数列的最后一个元素不能大于等于limit
>     3. 增量为delta，当delta未指定时，默认为1
* 参数:
  * start: 开始数值，int32类型数字，生成数列的第一个元素的值
  * limit: 最大值，int32类型数字，生成数列的最大值，最后一个元素肯定小于改值
  * delta: 增量，int32类型数字．该参数可选，默认为１
  * name: 别名(可选)
* 返回:
  * tensor对象．以start开始，增量为delta，最大不大于等于limit的int32类型数列
常用调用方法：
````

````
