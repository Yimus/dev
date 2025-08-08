package com.example.inventory.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    public InventoryEntity getInventoryByName(String name) {
        return inventoryMapper.selectOne(new QueryWrapper<InventoryEntity>().eq("name", name));
    }

    @Override
    public int updateInventory(InventoryEntity inventory) {
        return inventoryMapper.updateById(inventory);
    }
}
