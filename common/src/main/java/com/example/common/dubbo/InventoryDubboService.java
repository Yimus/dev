package com.example.common.dubbo;

import com.example.common.entity.InventoryEntity;

public interface InventoryDubboService {
    InventoryEntity getInventoryById(Long id);
}
