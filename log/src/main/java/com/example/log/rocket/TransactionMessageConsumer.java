package com.example.log.rocket;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "inventory-transaction", consumerGroup = "transaction-group")
public class TransactionMessageConsumer implements RocketMQListener<String> {

    private static final Logger logger = LoggerFactory.getLogger(TransactionMessageConsumer.class);

    @Override
    public void onMessage(String message) {
        logger.info("transaction-group get topic : inventory-transaction, message : {}",  message);
    }
}
