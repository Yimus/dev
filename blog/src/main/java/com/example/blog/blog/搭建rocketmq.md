### docker-compose

```yaml
services:
  namesrv:
    image: apache/rocketmq:latest
    container_name: rocketmq-namesrv
    ports:
      - "9876:9876"
    command: ["sh", "mqnamesrv"]

  broker:
    image: apache/rocketmq:latest
    container_name: rocketmq-broker
    hostname: broker
    ports:
      - "10909:10909"
      - "10911:10911"
      - "10912:10912"
    command: ["sh", "mqbroker", "-n", "namesrv:9876", "-c", "/home/rocketmq/broker.conf"]
    depends_on:
      - namesrv
    volumes:
      - ./broker.conf:/home/rocketmq/broker.conf

  proxy:
    image: apache/rocketmq:latest
    container_name: rocketmq-proxy
    ports:
      - "8080:8080"
      - "8081:8081"
    command: ["sh", "mqproxy", "-n", "namesrv:9876", "-pc", "/home/rocketmq/rmq-proxy.json"]
    depends_on:
      - namesrv
      - broker
    volumes:
      - ./rmq-proxy.json:/home/rocketmq/rmq-proxy.json

  dashboard:
    image: apacherocketmq/rocketmq-dashboard:latest
    container_name: rocketmq-dashboard
    ports:
      - "8180:8080"
    environment:
      - JAVA_OPTS=-Drocketmq.namesrv.addr=namesrv:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false
    depends_on:
      - namesrv
    restart: unless-stopped
```

### broker.conf

```
# 集群名称
brokerClusterName=DefaultCluster
# Broker名称
brokerName=broker-a
# Broker ID (0表示Master，>0表示Slave)
brokerId=0

# NameServer地址
namesrvAddr=namesrv:9876

# 删除文件时间点，默认凌晨4点
deleteWhen=04

# 文件保留时间，默认48小时
fileReservedTime=120

# Broker角色
brokerRole=ASYNC_MASTER

# 刷盘方式
flushDiskType=ASYNC_FLUSH

# 监听端口
listenPort=10911

# 存储路径
# storePathRootDir=/opt/store
# storePathCommitLog=/opt/store/commitlog

# 是否允许Broker自动创建Topic
autoCreateTopicEnable=true

# 是否允许Broker自动创建订阅组
autoCreateSubscriptionGroup=true

# 发送消息线程池数量
sendMessageThreadPoolNums=128

# 拉取消息线程池数量
pullMessageThreadPoolNums=128

# Broker对外服务的监听端口
brokerIP1=外网IP(宿主机IP)

# 启用定时消息
timerWheelEnable=true
```

### rmq-proxy.json

```json
{
  "rocketMQClusterName": "DefaultCluster",
  "namesrvAddr": "namesrv:9876",
  "grpcServerPort": 8081,
  "httpServerPort": 8080,
  "jmxRemotePort": 5000,
  "localServePort": -1,
  "enableACL": false,
  "accessKey": "",
  "secretKey": "",
  "maxMessageSize": 4194304,
  "sendMessageThreadPoolNums": 16,
  "sendMessageThreadPoolQueueCapacity": 10000,
  "pullMessageThreadPoolNums": 16,
  "pullMessageThreadPoolQueueCapacity": 10000,
  "queryMessageThreadPoolNums": 8,
  "queryMessageThreadPoolQueueCapacity": 1000,
  "ackMessageThreadPoolNums": 8,
  "ackMessageThreadPoolQueueCapacity": 1000,
  "forwardMessageThreadPoolNums": 8,
  "forwardMessageThreadPoolQueueCapacity": 1000,
  "transactionHeartbeatThreadPoolNums": 4,
  "transactionHeartbeatThreadPoolQueueCapacity": 1000,
  "endTransactionThreadPoolNums": 8,
  "endTransactionThreadPoolQueueCapacity": 1000,
  "adminBrokerThreadPoolNums": 16,
  "adminBrokerThreadPoolQueueCapacity": 10000,
  "clientManagerThreadPoolNums": 4,
  "clientManagerThreadPoolQueueCapacity": 1000,
  "heartbeatThreadPoolNums": 4,
  "heartbeatThreadPoolQueueCapacity": 1000,
  "consumerManagerThreadPoolNums": 4,
  "consumerManagerThreadPoolQueueCapacity": 1000,
  "topicQueueMappingCleanInterval": 120000
}
```

