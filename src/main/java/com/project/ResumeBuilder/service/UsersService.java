package com.project.ResumeBuilder.service;

import com.project.ResumeBuilder.constants.ConstantMessage;
import com.project.ResumeBuilder.dtoconvertor.DtoConvertor;
import com.project.ResumeBuilder.entities.Users;
import com.project.ResumeBuilder.enums.UserRole;
import com.project.ResumeBuilder.exception.ResourceConflictException;
import com.project.ResumeBuilder.exception.ResourceInvalidException;
import com.project.ResumeBuilder.exception.ResourceNotFoundException;
import com.project.ResumeBuilder.indto.LoginRequest;
import com.project.ResumeBuilder.indto.RegisterRequest;
import com.project.ResumeBuilder.indto.UpdateUserRequest;
import com.project.ResumeBuilder.outdto.LoginResponse;
import com.project.ResumeBuilder.outdto.UserResponse;
import com.project.ResumeBuilder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String register(RegisterRequest registerRequest) {
        try {
            if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
                throw new ResourceConflictException(ConstantMessage.USER_ALREADY_EXISTS);
            }
            registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
            Users user = DtoConvertor.convertToEntity(registerRequest);
            userRepository.save(user);
            return ConstantMessage.USER_REGISTERED_SUCCESSFULLY;
        } catch (ResourceConflictException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {

        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                Users user = userRepository.findByEmail(loginRequest.getEmail());
                UserRole role = user.getRole();
                LoginResponse loginResponse = DtoConvertor.convertToLoginResponse(user);
                loginResponse.setToken(jwtService.generateToken(loginRequest.getEmail(), role));
                return loginResponse;
            }
            throw new ResourceInvalidException("Invalid Credentials");
        } catch (ResourceInvalidException ex) {
            throw ex;
        }
    }

    public UserResponse findById(long userId) {
        try {
            Optional<Users> user = userRepository.findById(userId);
            if (user.isPresent()) {
                Users users = user.get();
                UserResponse userResponse = DtoConvertor.convertToResponse(users);
                return userResponse;
            }
            throw new ResourceNotFoundException(ConstantMessage.USER_NOT_FOUND);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    public List<UserResponse> findAll() {
        try {
            List<Users> users = userRepository.findAll();
            List<UserResponse> userResponses = new ArrayList<>();
            for (Users user : users) {
                UserResponse userResponse = DtoConvertor.convertToResponse(user);
                userResponses.add(userResponse);
            }
            if(users.isEmpty()) {
                throw new ResourceNotFoundException(ConstantMessage.USER_NOT_FOUND);
            }
            return userResponses;
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    public String updateUser(long userId, UpdateUserRequest updateUserRequest) {
        try {
            Optional<Users> user = userRepository.findById(userId);
            if(user.isPresent()) {
                Users updatedUser = user.get();
                if (!Objects.equals(updatedUser.getEmail(), updateUserRequest.getEmail())) {
                    if (userRepository.findByEmail(updateUserRequest.getEmail()) != null) {
                        throw new ResourceConflictException(ConstantMessage.USER_ALREADY_EXISTS);
                    }
                }
                updatedUser.setName(updateUserRequest.getName());
                updatedUser.setEmail(updateUserRequest.getEmail());
                userRepository.save(updatedUser);
                return ConstantMessage.USER_UPDATED_SUCCESSFULLY;
            }
            return ConstantMessage.FAILED_TO_UPDATE_USER;
        } catch (ResourceConflictException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
