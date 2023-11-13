package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.controller.data.CreateUserRequestBody;
import com.codecool.oidascriptplatform.model.User;
import com.codecool.oidascriptplatform.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserRequestBody userData) {
        return userService.registerUser(userData);
    }
}
