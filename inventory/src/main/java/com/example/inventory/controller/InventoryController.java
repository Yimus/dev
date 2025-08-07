package com.example.inventory.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.entity.InventoryEntity;
import com.example.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/inventory/add")
    public ResponseEntity<String> addInventory(@RequestBody InventoryEntity inventoryEntity) {
        QueryWrapper<InventoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", inventoryEntity.getId())
                .or()
                .eq("name", inventoryEntity.getName());
        InventoryEntity one = inventoryService.getOne(wrapper);
        if (one != null) {
            one.setCount(inventoryEntity.getCount() + one.getCount());
            inventoryService.updateById(one);
            return ResponseEntity.ok("update success");
        }
        inventoryService.save(inventoryEntity);
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
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.ok("no such inventory");
    }

    @PostMapping("/inventory/search")
    public ResponseEntity<InventoryEntity> searchInventory(@RequestBody InventoryEntity inventoryEntity) {
        QueryWrapper<InventoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", inventoryEntity.getId())
                .or()
                .eq("name", inventoryEntity.getName());
        InventoryEntity one = inventoryService.getOne(wrapper);
        LOGGER.info("search inventory:{}", one);
        return ResponseEntity.ok(one);
    }
}
