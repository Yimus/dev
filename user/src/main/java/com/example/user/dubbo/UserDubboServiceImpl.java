package com.example.user.dubbo;

import com.example.common.dubbo.UserDubboService;
import com.example.common.entity.UserEntity;
import com.example.user.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserDubboServiceImpl implements UserDubboService {

    private final UserMapper userMapper;

    public UserDubboServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userMapper.selectById(id);
    }
}
