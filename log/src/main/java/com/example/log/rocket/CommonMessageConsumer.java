package com.example.log.rocket;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "inventory", consumerGroup = "common-group")
public class CommonMessageConsumer implements RocketMQListener<String> {

    private static final Logger logger = LoggerFactory.getLogger(CommonMessageConsumer.class);

    @Override
    public void onMessage(String message) {
        logger.info("common-group get topic : inventory, message : {}",  message);
    }
}
