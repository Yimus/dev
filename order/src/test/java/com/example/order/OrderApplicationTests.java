package com.example.order;

import com.example.order.dto.Orders;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OrderApplicationTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(OrderApplicationTests.class);

    @Test
    void contextLoads() {
        Orders orders = new Orders();
        orders.setOrderId(1L);
        orders.setName("test order");
        orders.setUserId(1L);
        LOGGER.info("order id:{}", orders.getOrderId());
        LOGGER.info("order name:{}", orders.getName());
        LOGGER.info("order user id:{}", orders.getUserId());
    }
}
