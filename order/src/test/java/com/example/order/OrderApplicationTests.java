package com.example.order;

import com.example.common.dubbo.InventoryDubboService;
import com.example.common.dubbo.UserDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OrderApplicationTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(OrderApplicationTests.class);

    @DubboReference
    UserDubboService userDubboService;

    @DubboReference
    InventoryDubboService inventoryDubboService;

    @Test
    void contextLoads() {
        LOGGER.info("userDubboService getUserById {}", userDubboService.getUserById(1L));
        LOGGER.info("inventoryDubboService getInventoryById {}", inventoryDubboService.getInventoryById(1L));
    }
}
