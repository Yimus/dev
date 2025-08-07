package com.example.inventory.dubbo;

import com.example.common.dubbo.InventoryDubboService;
import com.example.common.entity.InventoryEntity;
import com.example.inventory.mapper.InventoryMapper;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InventoryDubboServiceImpl implements InventoryDubboService {

    private final InventoryMapper inventoryMapper;

    public InventoryDubboServiceImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public InventoryEntity getInventoryById(Long id) {
        return inventoryMapper.selectById(id);
    }
}
