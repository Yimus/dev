package com.example.log.rocket;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "inventory-order", consumerGroup = "orderly-group", consumeMode = ConsumeMode.ORDERLY)
public class OrderlyMessageConsumer implements RocketMQListener<String> {
    private static final Logger logger = LoggerFactory.getLogger(OrderlyMessageConsumer.class);

    @Override
    public void onMessage(String message) {
        logger.info("orderly-group get topic : inventory-order, message : {}", message);
    }
}
