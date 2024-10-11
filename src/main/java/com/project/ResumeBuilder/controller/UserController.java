package com.project.ResumeBuilder.controller;


import com.project.ResumeBuilder.indto.LoginInDTO;
import com.project.ResumeBuilder.indto.RegisterInDTO;
import com.project.ResumeBuilder.indto.UpdateUserInDTO;
import com.project.ResumeBuilder.outdto.LoginOutDTO;
import com.project.ResumeBuilder.outdto.SuccessOutDTO;
import com.project.ResumeBuilder.outdto.UserOutDTO;
import com.project.ResumeBuilder.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<SuccessOutDTO> registerUser(@RequestBody RegisterInDTO registerInDTO) {
        String response =  usersService.register(registerInDTO);
        SuccessOutDTO successOutDTO = new SuccessOutDTO(response);
        return ResponseEntity.status(HttpStatus.OK).body(successOutDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutDTO> login(@RequestBody LoginInDTO loginInDTO) {
        LoginOutDTO loginOutDTO =  usersService.login(loginInDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loginOutDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutDTO> findById(@PathVariable("id") long userId) {
        UserOutDTO user = usersService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserOutDTO>> getAllUser() {
        List<UserOutDTO> userRespons = usersService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userRespons);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<SuccessOutDTO> updateUser(@PathVariable("userId") long usedId, @RequestBody UpdateUserInDTO updateUserInDTO) {
        String response = usersService.updateUser(usedId, updateUserInDTO);
        SuccessOutDTO successOutDTO = new SuccessOutDTO(response);
        return ResponseEntity.status(HttpStatus.OK).body(successOutDTO);
    }
}
