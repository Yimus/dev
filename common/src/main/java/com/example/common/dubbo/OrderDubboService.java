package com.example.common.dubbo;

import com.example.common.entity.OrderEntity;

import java.util.List;

public interface OrderDubboService {
    List<OrderEntity> getOrderByUserId(Long id);
}
