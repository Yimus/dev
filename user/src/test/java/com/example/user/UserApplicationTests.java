package com.example.user;

import com.example.common.entity.OrderEntity;
import com.example.user.controller.UserController;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


@SpringBootTest
class UserApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApplicationTests.class);

    @Resource
    UserController userController;

    @Resource
    ThreadPoolExecutor threadPoolExecutor;

    @Test
    void contextLoads() {
        LOGGER.info("user application test");
    }

    @Test
    void testLock() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<Future<ResponseEntity<String>>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<ResponseEntity<String>> future = threadPoolExecutor.submit(() -> {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setUserId(2L);
                orderEntity.setName("可口可乐");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return userController.createOrder(orderEntity);
            });
            list.add(future);
        }
        countDownLatch.countDown();
        for (Future<ResponseEntity<String>> future : list) {
            ResponseEntity<String> response = null;
            try {
                response = future.get();
            } catch (Exception e) {
                LOGGER.error("get response error", e);
            }
            LOGGER.info("response:{}", response);
        }
    }
}
