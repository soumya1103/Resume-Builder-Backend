package com.project.ResumeBuilder.controller;


import com.project.ResumeBuilder.indto.LoginRequest;
import com.project.ResumeBuilder.indto.RegisterRequest;
import com.project.ResumeBuilder.outdto.SuccessResponse;
import com.project.ResumeBuilder.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        String response =  usersService.register(registerRequest);
        SuccessResponse successResponse = new SuccessResponse(response);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> login(@RequestBody LoginRequest loginRequest) {
        String response =  usersService.login(loginRequest);
        SuccessResponse successResponse = new SuccessResponse(response);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
