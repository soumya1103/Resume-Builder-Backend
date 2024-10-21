package com.project.ResumeBuilder.service;

import com.project.ResumeBuilder.constants.ConstantMessage;
import com.project.ResumeBuilder.dtoconvertor.DtoConvertor;
import com.project.ResumeBuilder.entities.Users;
import com.project.ResumeBuilder.enums.Gender;
import com.project.ResumeBuilder.enums.UserRole;
import com.project.ResumeBuilder.exception.ResourceConflictException;
import com.project.ResumeBuilder.exception.ResourceInvalidException;
import com.project.ResumeBuilder.exception.ResourceNotFoundException;
import com.project.ResumeBuilder.dtos.LoginInDTO;
import com.project.ResumeBuilder.dtos.RegisterInDTO;
import com.project.ResumeBuilder.dtos.UpdateUserInDTO;
import com.project.ResumeBuilder.dtos.LoginOutDTO;
import com.project.ResumeBuilder.dtos.UserOutDTO;
import com.project.ResumeBuilder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            byte[] decodedBytes = Base64.getDecoder().decode(loginInDTO.getPassword());
            String decodedPassword = new String(decodedBytes);
            loginInDTO.setPassword(decodedPassword);
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

    public String updateUser(long userId, UpdateUserInDTO updateUserInDTO, MultipartFile image) {
        try {
            Optional<Users> user = userRepository.findById(userId);
            if(user.isPresent()) {
                Users updatedUser = user.get();
                if (!updateUserInDTO.getGender().isEmpty()) {
                    if (!Objects.equals(updateUserInDTO.getGender(), "MALE") && !Objects.equals(updateUserInDTO.getGender(), "FEMALE") && !Objects.equals(updateUserInDTO.getGender(), "TRANSGENDER")) {
                        throw new ResourceInvalidException("Valid Gender Required");
                    }
                }
                if (image != null && !image.isEmpty()) {
                    String contentType = image.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        throw new ResourceInvalidException("Valid Image Required");
                    }
                    updatedUser.setImage(image.getBytes());
                }
                updatedUser.setBio(updateUserInDTO.getBio());
                updatedUser.setAddress(updateUserInDTO.getAddress());
                updatedUser.setDob(updateUserInDTO.getDob());
                updatedUser.setPhone(updateUserInDTO.getPhone());
                Gender gender = Gender.valueOf(updateUserInDTO.getGender());
                updatedUser.setGender(gender);
                userRepository.save(updatedUser);
                return ConstantMessage.USER_UPDATED_SUCCESSFULLY;
            }
            return ConstantMessage.FAILED_TO_UPDATE_USER;
        } catch (IOException e) {
            throw new RuntimeException("Failed to add image");
        } catch (ResourceInvalidException | ResourceConflictException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ConstantMessage.UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
