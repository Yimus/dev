package com.example.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.dto.OrderDTO;
import com.example.common.dubbo.InventoryDubboService;
import com.example.common.dubbo.OrderDubboService;
import com.example.common.entity.InventoryEntity;
import com.example.common.entity.OrderEntity;
import com.example.common.entity.UserEntity;
import com.example.common.exception.ResourceNotFoundException;
import com.example.user.config.MyAppConfig;
import com.example.user.feign.OrderFeignClient;
import com.example.user.rocket.RocketService;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final MyAppConfig myAppConfig;

    private final OrderFeignClient orderFeignClient;

    private final RedissonClient redissonClient;

    private final RocketService rocketService;

    @DubboReference
    private OrderDubboService orderDubboService;

    @DubboReference
    private InventoryDubboService inventoryDubboService;

    public UserController(UserService userService, MyAppConfig myAppConfig, OrderFeignClient orderFeignClient, RedissonClient redissonClient, RocketService rocketService) {
        this.userService = userService;
        this.myAppConfig = myAppConfig;
        this.orderFeignClient = orderFeignClient;
        this.redissonClient = redissonClient;
        this.rocketService = rocketService;
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

    @GetMapping("/user/orders/feign/{name}")
    public ResponseEntity<List<OrderDTO>> getUserOrdersByFeign(@PathVariable String name) {
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("name", name));
        List<OrderEntity> orders = orderFeignClient.getOrderByUserId(user.getId());
        return createOrderDTOResp(user, orders);
    }

    @GetMapping("/user/orders/dubbo/{name}")
    public ResponseEntity<List<OrderDTO>> getUserOrdersByDubbo(@PathVariable String name) {
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("name", name));
        List<OrderEntity> orders = orderDubboService.getOrderByUserId(user.getId());
        return createOrderDTOResp(user, orders);
    }

    @PostMapping("/user/create/order")
    public ResponseEntity<String> createOrder(@RequestBody OrderEntity orderEntity) {
        RLock lock = redissonClient.getLock("create:order:key:" + orderEntity.getUserId() + ":" + orderEntity.getName());
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock();
            if (!isLocked) {
                return ResponseEntity.ok("lock failed");
            }
            InventoryEntity inventory = inventoryDubboService.getInventoryByName(orderEntity.getName());
            if (inventory == null || inventory.getCount() == 0) {
                return ResponseEntity.ok("inventory is not enough");
            }
            int created = orderDubboService.creatOrder(orderEntity);
            if (created == 0) {
                return ResponseEntity.ok("create order failed");
            }
            inventory.setCount(inventory.getCount() - 1);
            int updated = inventoryDubboService.updateInventory(inventory);
            if (updated == 0) {
                return ResponseEntity.ok("update inventory failed");
            }
            TransactionSendResult transactionSendResult = rocketService.sendTransactionMessage("inventory-transaction", "create", "create order success userId : " + orderEntity.getUserId() + ", name : " + orderEntity.getName(), orderEntity.getUserId() + ":" + orderEntity.getName());
            if (transactionSendResult != null && transactionSendResult.getLocalTransactionState().equals(LocalTransactionState.COMMIT_MESSAGE)) {
                LOGGER.info("rocketmq transaction send success");
            }
            return ResponseEntity.ok("success");
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }

    private ResponseEntity<List<OrderDTO>> createOrderDTOResp(UserEntity user, List<OrderEntity> orders) {
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
