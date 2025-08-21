package com.example.inventory;

import com.example.common.dto.OrderDTO;
import com.example.common.dubbo.InventoryDubboService;
import com.example.common.entity.*;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InventoryApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryApplicationTests.class);

    @Resource
    private Earth earth;

    @Resource
    InventoryDubboService inventoryDubboService;

    @Test
    void contextLoads() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(1L);
        orderEntity.setName("test order");
        orderEntity.setUserId(1L);
        LOGGER.info("order id:{}", orderEntity.getOrderId());
        LOGGER.info("order name:{}", orderEntity.getName());
        LOGGER.info("order user id:{}", orderEntity.getUserId());

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

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(1L);
        orderDTO.setGoodName("test good");
        orderDTO.setUserId(1L);
        orderDTO.setUserName("test user");
        orderDTO.setUserEmail("123");
        LOGGER.info("order dto id:{}", orderDTO.getOrderId());
        LOGGER.info("order dto good name:{}", orderDTO.getGoodName());
        LOGGER.info("order dto user id:{}", orderDTO.getUserId());
        LOGGER.info("order dto user name:{}", orderDTO.getUserName());
        LOGGER.info("order dto user email:{}", orderDTO.getUserEmail());

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1L);
        inventoryEntity.setName("test inventory");
        inventoryEntity.setCount(100L);
        LOGGER.info("inventory id:{}", inventoryEntity.getId());
        LOGGER.info("inventory name:{}", inventoryEntity.getName());
        LOGGER.info("inventory count:{}", inventoryEntity.getCount());
    }

    @Test
    public void testInventoryDubboService () {
        InventoryEntity inventory = inventoryDubboService.getInventoryByName("可口可乐");
        LOGGER.info("inventory:{}", inventory);
    }
}
