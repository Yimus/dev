### docker compose

```yaml
services:
  redis:
    image: redis:latest
    container_name: redis
    hostname: redis
    restart: no
    ports:
      - "6379:6379"
    volumes:
      # 持久化数据存储
      - ./data:/data
      # 配置文件挂载（可选）
      - ./redis.conf:/etc/redis/redis.conf
    command: redis-server /etc/redis/redis.conf
    environment:
      # 设置时区
      - TZ=Asia/Shanghai
    # 健康检查
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 30s
```

### redis.conf

```
# Redis 配置文件
# 网络配置
bind 0.0.0.0
port 6379
timeout 0
tcp-backlog 511
# TCP keepalive (注意：参数名是 tcp-keepalive，不是 keepalive)
tcp-keepalive 300

# 一般配置
daemonize no
loglevel notice
logfile ""

# 数据持久化配置
# RDB配置
# 900秒内至少1个key发生变化则保存
save 900 1
# 300秒内至少10个key发生变化则保存     
save 300 10  
# 60秒内至少10000个key发生变化则保存  
save 60 10000  

rdbcompression yes
rdbchecksum yes
dbfilename dump.rdb
dir /data

# AOF配置（可选，更安全的持久化）
appendonly yes
appendfilename "appendonly.aof"
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb

# 内存管理
maxmemory-policy allkeys-lru

# 安全配置
# 设置密码（建议启用）
requirepass redis

# 客户端连接配置
maxclients 10000
tcp-backlog 511

# 慢查询日志
slowlog-log-slower-than 10000
slowlog-max-len 128

# 其他优化配置
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
list-max-ziplist-size -2
set-max-intset-entries 512
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
```