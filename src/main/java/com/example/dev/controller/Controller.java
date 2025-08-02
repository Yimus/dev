package com.example.dev.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dev.Exception.ResourceNotFoundException;
import com.example.dev.dto.User;
import com.example.dev.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {
    public static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody @Valid User user) {
        userService.save(user);
        LOGGER.info("add user:{}", user);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public User getUser(@PathVariable String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        User user = userService.getOne(wrapper);
        if (null == user) {
            throw new ResourceNotFoundException("User not found with name: " + name);
        }
        LOGGER.info("get user:{}", user);
        return user;
    }
}
