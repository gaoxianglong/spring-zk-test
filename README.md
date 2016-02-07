# spring-zk-test
spring利用zookeeper作为配置中心Demo。

# 原理
首先动态加载相关bean，如果配置中心发生改变，对应的watcher监听到事件后，客户端则对相关bean进行重新注册，并且从配置中心获取到了最新数据，然后客户端直接调用getBean()方法获取相关bean实例，确保不再是之前引用。
