package com.example.user;

import com.example.common.entity.OrderEntity;
import com.example.user.controller.UserController;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class UserApplicationTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserApplicationTests.class);

    @Resource
    UserController userController;

    @Test
    void contextLoads() {
        LOGGER.info("user application test");
    }

    @Test
    void testLock() {
        try (ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100))) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            for (int i = 0; i < 100; i++) {
                threadPoolExecutor.execute(() -> {
                    OrderEntity orderEntity = new OrderEntity();
                    orderEntity.setUserId(1L);
                    orderEntity.setName("可口可乐");
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    ResponseEntity<String> response = userController.createOrder(orderEntity);
                    LOGGER.info("create order:{}", response);
                });
            }
            countDownLatch.countDown();
        } catch (Exception e) {
            LOGGER.error("test lock error", e);
        }
    }
}
