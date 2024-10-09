package com.project.ResumeBuilder.service;

import com.project.ResumeBuilder.constants.ConstantMessage;
import com.project.ResumeBuilder.dtoconvertor.DtoConvertor;
import com.project.ResumeBuilder.entities.Users;
import com.project.ResumeBuilder.enums.UserRole;
import com.project.ResumeBuilder.exception.ResourceAlreadyExistsException;
import com.project.ResumeBuilder.exception.ResourceNotFoundException;
import com.project.ResumeBuilder.indto.LoginRequest;
import com.project.ResumeBuilder.indto.RegisterRequest;
import com.project.ResumeBuilder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
                throw new ResourceAlreadyExistsException(ConstantMessage.USER_ALREADY_EXISTS);
            }
            registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
            Users user = DtoConvertor.convertToEntity(registerRequest);
            userRepository.save(user);
            return ConstantMessage.USER_REGISTERED_SUCCESSFULLY;
        } catch (ResourceAlreadyExistsException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            Users user = userRepository.findByEmail(loginRequest.getEmail());
            UserRole role = user.getRole();
            return jwtService.generateToken(loginRequest.getEmail(), role);
        } else {
            return ConstantMessage.INVALID_CREDENTIALS;
        }
    }

    public Users findById(long userId) {
        try {
            Optional<Users> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return user.get();
            }
            throw new ResourceNotFoundException(ConstantMessage.USER_NOT_FOUND);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
