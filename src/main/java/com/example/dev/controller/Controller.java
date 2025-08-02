package com.example.dev.controller;

import com.example.dev.Exception.ResourceNotFoundException;
import com.example.dev.dto.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class Controller {

    public static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    Map<String, User> db = new HashMap<>(8);

    @PostMapping("/user/add")
    public ResponseEntity<String> hello(@RequestBody @Valid User user) {
        db.put(user.getName(), user);
        LOGGER.info("add user:{}", user);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public User getUser(@PathVariable String name) {
        User user = db.get(name);
        if (null == user) {
            throw new ResourceNotFoundException("User not found with name: " + name);
        }
        LOGGER.info("get user:{}", user);
        return user;
    }
}
