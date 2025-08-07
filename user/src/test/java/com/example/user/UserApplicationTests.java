package com.example.user;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserApplicationTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserApplicationTests.class);

    @Test
    void contextLoads() {
        LOGGER.info("user application test");
    }
}
