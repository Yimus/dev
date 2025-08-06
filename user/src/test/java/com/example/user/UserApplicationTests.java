package com.example.user;

import com.example.user.entity.Earth;
import com.example.user.entity.Sea;
import com.example.user.entity.UserEntity;
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

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("test user");
        userEntity.setEmail("123");
        LOGGER.info("user id:{}", userEntity.getId());
        LOGGER.info("user name:{}", userEntity.getName());
        LOGGER.info("user email:{}", userEntity.getEmail());
    }
}
