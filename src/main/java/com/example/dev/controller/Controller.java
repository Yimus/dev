package com.example.dev.controller;

import com.example.dev.Exception.ResourceNotFoundException;
import com.example.dev.dto.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class Controller {

    Map<String, User> db = new HashMap<>(8);

    @PostMapping("/add")
    public String hello(@RequestBody @Valid User user) {
        db.put(user.getName(), user);
        return "success";
    }

    @GetMapping("/{name}")
    public User getUser(@PathVariable String name) {
        User user = db.get(name);
        if (null == user) {
            throw new ResourceNotFoundException("User not found with name: " + name);
        }
        return user;
    }
}
