package com.example.inventory.rocket;


import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RocketService {
    private static final Logger logger = LoggerFactory.getLogger(RocketService.class);

    private final RocketMQTemplate rocketMQTemplate;

    public RocketService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public SendResult sendMessage(String topic, String tag, String message) {
        Message<String> msg = MessageBuilder.withPayload(message).build();
        SendResult sendResult = rocketMQTemplate.syncSend(topic + ":" + tag, msg);
        logger.info("sendMessage topic:{},tag:{},message:{},sendResult:{}", topic, tag, message, sendResult);
        return sendResult;
    }

    /**
     * 发送延时消息
     *
     * @param topic      主题
     * @param message    消息内容
     * @param delayLevel 延时级别 (1-18)
     */
    public SendResult sendDelayedMessage(String topic, String tag, Object message, int delayLevel) {
        Message<Object> msg = MessageBuilder.withPayload(message).build();
        // 延时级别：1=1s, 2=5s, 3=10s, 4=30s, 5=1m, 6=2m, 7=3m, 8=4m, 9=5m, 10=6m,
        // 11=7m, 12=8m, 13=9m, 14=10m, 15=20m, 16=30m, 17=1h, 18=2h
        SendResult sendResult = rocketMQTemplate.syncSend(topic + ":" + tag, msg, 3000, delayLevel);
        logger.info("sendDelayedMessage topic:{},tag:{},message:{},sendResult:{}", topic, tag, message, sendResult);
        return sendResult;
    }

    /**
     * 发送定时消息 (RocketMQ 5.x 新特性)
     */
    public SendResult sendTimedMessage(String topic, String tag, Object message, long deliverTime) {
        Message<Object> msg = MessageBuilder.withPayload(message).build();
        SendResult sendResult = rocketMQTemplate.syncSendDelayTimeMills(topic + ":" + tag, msg, deliverTime);
        logger.info("sendTimedMessage topic:{},tag:{},message:{},sendResult:{}", topic, tag, message, sendResult);
        return sendResult;
    }

    /**
     * 发送顺序消息
     *
     * @param topic       主题
     * @param message     消息内容
     * @param shardingKey 分片键，相同分片键的消息会被发送到同一个队列
     */
    public SendResult sendOrderlyMessage(String topic, String tag, Object message, String shardingKey) {
        Message<Object> msg = MessageBuilder.withPayload(message).build();
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(topic + ":" + tag, msg, shardingKey);
        logger.info("sendOrderlyMessage topic:{},tag:{},message:{},sendResult:{}", topic, tag, message, sendResult);
        return sendResult;
    }

    /**
     * 异步发送顺序消息
     */
    public void asyncSendOrderlyMessage(String topic, String tag, Object message, String shardingKey) {
        Message<Object> msg = MessageBuilder.withPayload(message).build();
        rocketMQTemplate.asyncSendOrderly(topic + ":" + tag, msg, shardingKey, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("asyncSendOrderlyMessage topic:{},tag:{},message:{},sendResult:{} success", topic, tag, message, sendResult);
            }

            @Override
            public void onException(Throwable e) {
                logger.info("asyncSendOrderlyMessage topic:{},tag:{},message:{},sendResult:{} fail", topic, tag, message, e.getMessage());
            }
        });
    }

}
