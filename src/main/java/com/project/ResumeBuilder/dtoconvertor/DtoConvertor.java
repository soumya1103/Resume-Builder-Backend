package com.project.ResumeBuilder.dtoconvertor;

import com.project.ResumeBuilder.entities.Users;
import com.project.ResumeBuilder.indto.RegisterRequest;
import com.project.ResumeBuilder.outdto.UserResponse;

public class DtoConvertor{
    public static UserResponse convertToResponse(Users users) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(users.getUserId());
        userResponse.setUserEmail(users.getEmail());
        userResponse.setUserRole(users.getRole());
        return userResponse;
    }
    public static Users convertToEntity(RegisterRequest registerRequest) {
        Users users = new Users();
        users.setEmail(registerRequest.getEmail());
        users.setName(registerRequest.getName());
        users.setPassword(registerRequest.getPassword());
        users.setRole(registerRequest.getRole());
        return users;
    }
}
