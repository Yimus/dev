package com.example.order.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.dubbo.OrderDubboService;
import com.example.common.entity.OrderEntity;
import com.example.order.mapper.OrderMapper;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService
public class OrderDubboServiceImpl implements OrderDubboService {

    private final OrderMapper orderMapper;

    public OrderDubboServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderEntity> getOrderByUserId(Long id) {
        return orderMapper.selectList(new QueryWrapper<OrderEntity>().eq("user_id", id));
    }

    public int creatOrder(OrderEntity order) {
        return orderMapper.insert(order);
    }
}
