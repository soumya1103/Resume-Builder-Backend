package com.project.ResumeBuilder.outdto;

import com.project.ResumeBuilder.enums.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private Long userId;
    private String email;
    private String name;
    private UserRole role;
}
