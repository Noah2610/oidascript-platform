package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.controller.data.CreateUserRequestBody;
import com.codecool.oidascriptplatform.model.User;
import com.codecool.oidascriptplatform.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String debug() {
        return "Hello World";
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserRequestBody userData) {
        System.out.println("HERE!");
        return userService.registerUser(userData);
    }
}
