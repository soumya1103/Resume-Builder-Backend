package com.project.ResumeBuilder.dtoconvertor;

import com.project.ResumeBuilder.entities.Users;
import com.project.ResumeBuilder.dtos.RegisterInDTO;
import com.project.ResumeBuilder.dtos.LoginOutDTO;
import com.project.ResumeBuilder.dtos.UserOutDTO;

public class DtoConvertor{

    public static LoginOutDTO convertToLoginResponse(Users users) {
        LoginOutDTO loginOutDTO = new LoginOutDTO();
        loginOutDTO.setUserId(users.getUserId());
        loginOutDTO.setEmail(users.getEmail());
        loginOutDTO.setName(users.getName());
        loginOutDTO.setRole(users.getRole());
        return loginOutDTO;
    }

    public static UserOutDTO convertToResponse(Users users) {
        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setUserId(users.getUserId());
        userOutDTO.setName(users.getName());
        userOutDTO.setEmail(users.getEmail());
        userOutDTO.setRole(users.getRole());
        return userOutDTO;
    }
    public static Users convertToEntity(RegisterInDTO registerInDTO) {
        Users users = new Users();
        users.setEmail(registerInDTO.getEmail());
        users.setName(registerInDTO.getName());
        users.setPassword(registerInDTO.getPassword());
        return users;
    }
}
