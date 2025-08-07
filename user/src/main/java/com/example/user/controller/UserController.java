package com.example.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.dto.OrderDTO;
import com.example.common.entity.OrderEntity;
import com.example.common.entity.UserEntity;
import com.example.common.exception.ResourceNotFoundException;
import com.example.user.config.MyAppConfig;
import com.example.user.feign.OrderFeignClient;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final MyAppConfig myAppConfig;

    private final OrderFeignClient orderFeignClient;

    public UserController(UserService userService, MyAppConfig myAppConfig, OrderFeignClient orderFeignClient) {
        this.userService = userService;
        this.myAppConfig = myAppConfig;
        this.orderFeignClient = orderFeignClient;
    }

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody @Valid UserEntity userEntity) {
        userService.save(userEntity);
        LOGGER.info("add user:{}", userEntity);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<List<UserEntity>> getUser(@PathVariable String name) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        List<UserEntity> list = userService.list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResourceNotFoundException("User not found with name: " + name);
        }
        LOGGER.info("get user:{}", list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/config")
    public ResponseEntity<MyAppConfig> getUserConfig() {
        return ResponseEntity.ok(myAppConfig);
    }

    @GetMapping("/user/orders/{name}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable String name) {
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("name", name));
        List<OrderEntity> orders = orderFeignClient.getOrderByUserId(user.getId());
        return ResponseEntity.ok(orders.stream()
                .map(o -> {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setOrderId(o.getOrderId());
                    orderDTO.setGoodName(o.getName());
                    orderDTO.setUserId(user.getId());
                    orderDTO.setUserName(user.getName());
                    orderDTO.setUserEmail(user.getEmail());
                    return orderDTO;
                })
                .toList());
    }
}
