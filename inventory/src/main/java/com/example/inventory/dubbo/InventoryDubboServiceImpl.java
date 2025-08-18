package com.example.inventory.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.dubbo.InventoryDubboService;
import com.example.common.entity.InventoryEntity;
import com.example.inventory.mapper.InventoryMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

@DubboService
public class InventoryDubboServiceImpl implements InventoryDubboService {

    private final InventoryMapper inventoryMapper;

    private final RedissonClient redissonClient;

    public InventoryDubboServiceImpl(InventoryMapper inventoryMapper, RedissonClient redissonClient) {
        this.inventoryMapper = inventoryMapper;
        this.redissonClient = redissonClient;
    }

    @Override
    public InventoryEntity getInventoryById(Long id) {
        return inventoryMapper.selectById(id);
    }

    public InventoryEntity getInventoryByName(String name) {
        RMap<String, InventoryEntity> map = redissonClient.getMap("inventory:key:name");
        InventoryEntity inventoryEntity = map.get(name);
        if (inventoryEntity == null) {
            inventoryEntity = inventoryMapper.selectOne(new QueryWrapper<InventoryEntity>().eq("name", name));
            map.put(name, inventoryEntity == null ? new InventoryEntity() : inventoryEntity);
//            map.expire(Duration.ofSeconds(RandomGenerator.getDefault().nextInt()));
            return inventoryEntity;
        }
        return inventoryEntity;
    }

    @Override
    public int updateInventory(InventoryEntity inventory) {
        int res = inventoryMapper.updateById(inventory);
        if (res > 0) {
            RMap<String, InventoryEntity> map = redissonClient.getMap("inventory:key:name");
            map.remove(inventory.getName());
        }
        return res;
    }
}
