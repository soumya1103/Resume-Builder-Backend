package com.project.ResumeBuilder.controller;


import com.project.ResumeBuilder.dtos.LoginInDTO;
import com.project.ResumeBuilder.dtos.RegisterInDTO;
import com.project.ResumeBuilder.dtos.UpdateUserInDTO;
import com.project.ResumeBuilder.dtos.LoginOutDTO;
import com.project.ResumeBuilder.dtos.SuccessOutDTO;
import com.project.ResumeBuilder.dtos.UserOutDTO;
import com.project.ResumeBuilder.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<SuccessOutDTO> registerUser(@Valid  @RequestBody RegisterInDTO registerInDTO) {
        String response =  usersService.register(registerInDTO);
        SuccessOutDTO successOutDTO = new SuccessOutDTO(response);
        return ResponseEntity.status(HttpStatus.OK).body(successOutDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutDTO> login(@Valid @RequestBody LoginInDTO loginInDTO) {
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
    public ResponseEntity<SuccessOutDTO> updateUser(@PathVariable("userId") long usedId, @ModelAttribute @Valid UpdateUserInDTO updateUserInDTO, @RequestParam(value = "image", required = false) final MultipartFile image) {
        String response = usersService.updateUser(usedId, updateUserInDTO, image);
        SuccessOutDTO successOutDTO = new SuccessOutDTO(response);
        return ResponseEntity.status(HttpStatus.OK).body(successOutDTO);
    }
}
