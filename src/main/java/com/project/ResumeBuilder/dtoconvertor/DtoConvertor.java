package com.project.ResumeBuilder.dtoconvertor;

import com.project.ResumeBuilder.indto.UserRequest;
import com.project.ResumeBuilder.outdto.UserResponse;
import com.project.ResumeBuilder.entities.User;

public class DtoConvertor{
    public static UserResponse convertToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUserEmail(user.getUserEmail());
        userResponse.setUserName(user.getUserName());
        userResponse.setUserRole(user.getUserRole());
        return userResponse;
    }
    public static User convertToEntity(UserRequest userRequest) {
        User user = new User();
        user.setUserEmail(userRequest.getUserEmail());
        user.setUserName(userRequest.getUserName());
        user.setUserPassword(userRequest.getUserPassword());
        user.setUserRole(userRequest.getUserRole());
        return user;
    }
}
