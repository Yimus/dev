package com.example.user.rocket;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RocketMQTransactionListener
public class RocketService implements RocketMQLocalTransactionListener {
    private static final Logger logger = LoggerFactory.getLogger(RocketService.class);

    private final RocketMQTemplate rocketMQTemplate;

    public RocketService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 发送事务消息
     */
    @Transactional
    public TransactionSendResult sendTransactionMessage(String topic, String tag, Object message, Object arg) {
        Message<Object> msg = MessageBuilder.withPayload(message).build();
        // 发送事务消息，第三个参数是传给事务监听器的参数
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(topic + ":" + tag, msg, arg);
        logger.info("sendTransactionMessage topic:{},tag:{},message:{},sendResult:{}", topic, tag, message, transactionSendResult);
        return transactionSendResult;
    }

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        // 执行本地事务
        logger.info("executeLocalTransaction message:{}", message);
        try {
            // 这里执行你的业务逻辑，比如数据库操作
            // 模拟业务处理
            if (localTransaction()) {
                logger.info("executeLocalTransaction message:{} success", message);
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                logger.info("executeLocalTransaction message:{} fail", message);
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } catch (Exception e) {
            logger.error("executeLocalTransaction message:{} error", message, e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        // 检查本地事务状态，用于消息回查
        logger.info("checkLocalTransaction message:{}", message);
        // 根据消息内容或其他标识查询本地事务状态
        if (localTransaction()) {
            logger.info("checkLocalTransaction message:{} success", message);
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            logger.info("checkLocalTransaction message:{} fail", message);
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    private boolean localTransaction() {
        logger.info("localTransaction");
        boolean res = new Random().nextBoolean();
        logger.info("localTransaction result:{}", res);
        return res;
    }
}
