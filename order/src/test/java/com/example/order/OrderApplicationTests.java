package com.example.order;

import com.example.order.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OrderApplicationTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(OrderApplicationTests.class);

    @Test
    void contextLoads() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(1L);
        orderEntity.setName("test order");
        orderEntity.setUserId(1L);
        LOGGER.info("order id:{}", orderEntity.getOrderId());
        LOGGER.info("order name:{}", orderEntity.getName());
        LOGGER.info("order user id:{}", orderEntity.getUserId());
    }
}
