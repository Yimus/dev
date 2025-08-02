package com.example.dev.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dev.dto.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
}
