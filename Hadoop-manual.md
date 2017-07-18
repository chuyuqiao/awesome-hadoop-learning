## [Secondary NameNode](http://blog.csdn.net/yangjjuan/article/details/71107012) ##

## [Yarn](http://www.cobub.com/the-selection-and-use-of-hadoop-yarn-scheduler/) ##

1. 如果业务逻辑比较简单或者刚接触Hadoop的时候建议使用FIFO调度器；如果需要控制部分应用的优先级同时又想要充分利用集群资源的情况下，建议使用Capacity调度器；如果想要多用户或者多队列公平的共享集群资源，那么就选用Fair调度器。希望大家能够根据业务所需选择合适的调度器。
