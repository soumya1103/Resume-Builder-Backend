package com.project.ResumeBuilder.controller;


import com.project.ResumeBuilder.indto.UserRequest;
import com.project.ResumeBuilder.outdto.CommonResponse;
import com.project.ResumeBuilder.outdto.UserResponse;
import com.project.ResumeBuilder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public CommonResponse registerUser(@RequestBody UserRequest userRequest) {
        return userService.addUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
