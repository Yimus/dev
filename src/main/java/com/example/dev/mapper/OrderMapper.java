package com.example.dev.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dev.dto.Orders;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
