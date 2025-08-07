package com.example.common.dubbo;

import com.example.common.entity.UserEntity;

public interface UserDubboService {
    UserEntity getUserById(Long id);
}
