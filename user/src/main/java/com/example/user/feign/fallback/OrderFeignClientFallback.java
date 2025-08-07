package com.example.user.feign.fallback;

import com.example.common.entity.OrderEntity;
import com.example.user.feign.OrderFeignClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderFeignClientFallback implements OrderFeignClient {
    @Override
    public List<OrderEntity> getOrderByUserId(Long id) {
        return new ArrayList<>();
    }
}
