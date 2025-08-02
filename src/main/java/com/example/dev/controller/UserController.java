package com.example.dev.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dev.Exception.ResourceNotFoundException;
import com.example.dev.dto.User;
import com.example.dev.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody @Valid User user) {
        userService.save(user);
        LOGGER.info("add user:{}", user);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<List<User>> getUser(@PathVariable String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        List<User> list = userService.list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResourceNotFoundException("User not found with name: " + name);
        }
        LOGGER.info("get user:{}", list);
        return ResponseEntity.ok(list);
    }
}
