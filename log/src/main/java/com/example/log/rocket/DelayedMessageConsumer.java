package com.example.log.rocket;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "inventory-delayed", consumerGroup = "delayed-group")
public class DelayedMessageConsumer implements RocketMQListener<String> {
    private static final Logger logger = LoggerFactory.getLogger(DelayedMessageConsumer.class);

    @Override
    public void onMessage(String message) {
        logger.info("delayed-group get topic : inventory-delayed, message : {}", message);
    }
}
