package com.example.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.order.dto.Orders;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
