package com.project.ResumeBuilder.service;



import com.project.ResumeBuilder.constants.ConstantMessage;
import com.project.ResumeBuilder.dtoconvertor.DtoConvertor;
import com.project.ResumeBuilder.entities.User;
import com.project.ResumeBuilder.exception.ResourceConflictException;
import com.project.ResumeBuilder.exception.ResourceNotFoundException;
import com.project.ResumeBuilder.indto.UserRequest;
import com.project.ResumeBuilder.outdto.CommonResponse;
import com.project.ResumeBuilder.outdto.UserResponse;
import com.project.ResumeBuilder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public CommonResponse addUser(final UserRequest userRequest) {

        User user = DtoConvertor.convertToEntity(userRequest);
        Optional<User> optionalUser = userRepository.findByUserEmail(userRequest.getUserEmail().toLowerCase());
        if (optionalUser.isPresent()) {
            throw new ResourceConflictException(ConstantMessage.ALREADY_EXISTS);
        }
        userRepository.save(user);
        return new CommonResponse(ConstantMessage.USER_REGISTERED_SUCCESS);
    }
    public UserResponse getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return DtoConvertor.convertToResponse(user.get());
        }
        throw new ResourceNotFoundException(ConstantMessage.USER_NOT_FOUND);

    }
}

