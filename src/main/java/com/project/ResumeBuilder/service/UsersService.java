package com.project.ResumeBuilder.service;

import com.project.ResumeBuilder.constants.ConstantMessage;
import com.project.ResumeBuilder.dtoconvertor.DtoConvertor;
import com.project.ResumeBuilder.entities.Users;
import com.project.ResumeBuilder.enums.UserRole;
import com.project.ResumeBuilder.exception.ResourceConflictException;
import com.project.ResumeBuilder.exception.ResourceInvalidException;
import com.project.ResumeBuilder.exception.ResourceNotFoundException;
import com.project.ResumeBuilder.indto.LoginInDTO;
import com.project.ResumeBuilder.indto.RegisterInDTO;
import com.project.ResumeBuilder.indto.UpdateUserInDTO;
import com.project.ResumeBuilder.outdto.LoginOutDTO;
import com.project.ResumeBuilder.outdto.UserOutDTO;
import com.project.ResumeBuilder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String register(RegisterInDTO registerInDTO) {
        try {
            if (userRepository.findByEmail(registerInDTO.getEmail()) != null) {
                throw new ResourceConflictException(ConstantMessage.USER_ALREADY_EXISTS);
            }
            UserRole role = UserRole.valueOf(registerInDTO.getRole());
            registerInDTO.setPassword(encoder.encode(registerInDTO.getPassword()));
            Users user = DtoConvertor.convertToEntity(registerInDTO);
            user.setRole(role);
            userRepository.save(user);
            return ConstantMessage.USER_REGISTERED_SUCCESSFULLY;
        } catch (IllegalArgumentException e) {
            throw new ResourceInvalidException("Invalid role. Allowed values: ROLE_HR, ROLE_EMPLOYEE");
        } catch (ResourceConflictException | ResourceInvalidException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    public LoginOutDTO login(LoginInDTO loginInDTO) {

        try {
         //   byte[] decodedBytes = Base64.getDecoder().decode(loginInDTO.getPassword());
          //  String decodedPassword = new String(decodedBytes);
           // loginInDTO.setPassword(decodedPassword);
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginInDTO.getEmail(), loginInDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                Users user = userRepository.findByEmail(loginInDTO.getEmail());
                UserRole role = user.getRole();
                LoginOutDTO loginOutDTO = DtoConvertor.convertToLoginResponse(user);
                loginOutDTO.setToken(jwtService.generateToken(loginInDTO.getEmail(), role));
                return loginOutDTO;
            }
            throw new ResourceInvalidException("Invalid Credentials");
        } catch (ResourceInvalidException ex) {
            throw ex;
        }
    }

    public UserOutDTO findById(long userId) {
        try {
            Optional<Users> user = userRepository.findById(userId);
            if (user.isPresent()) {
                Users users = user.get();
                UserOutDTO userOutDTO = DtoConvertor.convertToResponse(users);
                return userOutDTO;
            }
            throw new ResourceNotFoundException(ConstantMessage.USER_NOT_FOUND);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    public List<UserOutDTO> findAll() {
        try {
            List<Users> users = userRepository.findAll();
            List<UserOutDTO> userOutDTOS = new ArrayList<>();
            for (Users user : users) {
                UserOutDTO userOutDTO = DtoConvertor.convertToResponse(user);
                userOutDTOS.add(userOutDTO);
            }
            if(users.isEmpty()) {
                throw new ResourceNotFoundException(ConstantMessage.USER_NOT_FOUND);
            }
            return userOutDTOS;
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    public String updateUser(long userId, UpdateUserInDTO updateUserInDTO) {
        try {
            Optional<Users> user = userRepository.findById(userId);
            if(user.isPresent()) {
                Users updatedUser = user.get();
                if (!Objects.equals(updatedUser.getEmail(), updateUserInDTO.getEmail())) {
                    if (userRepository.findByEmail(updateUserInDTO.getEmail()) != null) {
                        throw new ResourceConflictException(ConstantMessage.USER_ALREADY_EXISTS);
                    }
                }
                updatedUser.setName(updateUserInDTO.getName());
                updatedUser.setEmail(updateUserInDTO.getEmail());
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
