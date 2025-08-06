package com.example.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.order.entity.OrderEntity;
import com.example.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    public static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/add")
    public ResponseEntity<String> addOrder(@RequestBody OrderEntity orderEntity) {
        orderService.save(orderEntity);
        LOGGER.info("add order:{}", orderEntity);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/orders/search")
    public ResponseEntity<List<OrderEntity>> searchOrder(@RequestBody OrderEntity orderEntity) {
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", orderEntity.getUserId());
        wrapper.eq("order_id", orderEntity.getOrderId());
        List<OrderEntity> list = orderService.list(wrapper);
        LOGGER.info("search order:{}", list);
        return ResponseEntity.ok(list);
    }
}
