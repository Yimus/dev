package com.example.order;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OrderApplicationTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(OrderApplicationTests.class);

    @Test
    void contextLoads() {
        LOGGER.info("order application test");
    }
}
