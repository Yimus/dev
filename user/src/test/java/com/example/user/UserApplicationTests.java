package com.example.user;

import com.example.user.dto.Earth;
import com.example.user.dto.Sea;
import com.example.user.dto.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserApplicationTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserApplicationTests.class);

    @Resource
    private Earth earth;

    @Test
    void contextLoads() {
        earth.setArea("test area");
        LOGGER.info("earth area:{}", earth.getArea());
        LOGGER.info("earth sea:{}", earth.getSea());
        LOGGER.info("earth sea:{}", earth.getSea());
        Sea sea = earth.getSea();
        sea.setName("test sea");
        LOGGER.info("sea name:{}", sea.getName());

        User user = new User();
        user.setId(1L);
        user.setName("test user");
        user.setEmail("123");
        LOGGER.info("user id:{}", user.getId());
        LOGGER.info("user name:{}", user.getName());
        LOGGER.info("user email:{}", user.getEmail());
    }
}
