package com.example.user.feign;

import com.example.common.entity.OrderEntity;
import com.example.user.feign.fallback.OrderFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order", fallback = OrderFeignClientFallback.class)
public interface OrderFeignClient {
    @GetMapping("/orders/user/{id}")
    List<OrderEntity> getOrderByUserId(@PathVariable("id") Long id);
}
