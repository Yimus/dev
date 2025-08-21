package com.example.inventory.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.entity.InventoryEntity;
import com.example.inventory.rocket.RocketService;
import com.example.inventory.service.InventoryService;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    private final RedissonClient redissonClient;

    private final RocketService rocketService;

    public InventoryController(InventoryService inventoryService, RedissonClient redissonClient, RocketService rocketService) {
        this.inventoryService = inventoryService;
        this.redissonClient = redissonClient;
        this.rocketService = rocketService;
    }

    @PostMapping("/inventory/add")
    public ResponseEntity<String> addInventory(@RequestBody InventoryEntity inventoryEntity) {
        QueryWrapper<InventoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", inventoryEntity.getId())
                .or()
                .eq("name", inventoryEntity.getName());
        InventoryEntity one = inventoryService.getOne(wrapper);
        SendResult sendResult;
        if (one != null) {
            one.setCount(inventoryEntity.getCount() + one.getCount());
            inventoryService.updateById(one);
            redissonClient.getMap("inventory:key:name").remove(inventoryEntity.getName());
            do {
                sendResult = rocketService.sendMessage("inventory", "add", "/inventory/add update : " + one);
            } while (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK);
            return ResponseEntity.ok("update success");
        }
        inventoryService.save(inventoryEntity);
        do {
            sendResult = rocketService.sendMessage("inventory", "add", "/inventory/add insert : " + inventoryEntity);
        } while (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK);
        return ResponseEntity.ok("insert success");
    }

    @PostMapping("/inventory/reduce")
    public ResponseEntity<String> reduceInventory(@RequestBody InventoryEntity inventoryEntity) {
        QueryWrapper<InventoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", inventoryEntity.getId())
                .or()
                .eq("name", inventoryEntity.getName());
        InventoryEntity one = inventoryService.getOne(wrapper);
        if (one != null) {
            one.setCount(one.getCount() - inventoryEntity.getCount());
            inventoryService.updateById(one);
            redissonClient.getMap("inventory:key:name").remove(inventoryEntity.getName());
            SendResult sendResult;
            do {
                sendResult = rocketService.sendOrderlyMessage("inventory-order", "reduce", "/inventory/reduce update : " + one, inventoryEntity.getName());
            } while (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK);
            return ResponseEntity.ok("success");
        }
        rocketService.asyncSendOrderlyMessage("inventory-order", "reduce", "/inventory/reduce no such inventory " + inventoryEntity.getName(), inventoryEntity.getName());
        return ResponseEntity.ok("no such inventory");
    }

    @PostMapping("/inventory/search")
    public ResponseEntity<InventoryEntity> searchInventory(@RequestBody InventoryEntity inventoryEntity) {
        RMap<String, InventoryEntity> map = redissonClient.getMap("inventory:key:name");
        InventoryEntity inventory = map.get(inventoryEntity.getName());
        SendResult sendResult;
        if (inventory != null) {
            do {
                sendResult = rocketService.sendTimedMessage("inventory-delayed", "search", "/inventory/search : " + inventory, 2000);
            } while (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK);
            return ResponseEntity.ok(inventory);
        }
        QueryWrapper<InventoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", inventoryEntity.getId())
                .or()
                .eq("name", inventoryEntity.getName());
        InventoryEntity one = inventoryService.getOne(wrapper);
        LOGGER.info("search inventory:{}", one);
        map.put(inventoryEntity.getName(), one);
//        map.expire(Duration.ofSeconds(RandomGenerator.getDefault().nextInt()));
        do {
            sendResult = rocketService.sendDelayedMessage("inventory-delayed", "search", "/inventory/search : " + one, 2);
        } while (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK);
        return ResponseEntity.ok(one);
    }
}
