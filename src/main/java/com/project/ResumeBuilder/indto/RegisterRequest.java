package com.project.ResumeBuilder.indto;

import com.project.ResumeBuilder.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class RegisterRequest {

    private Long userId;

    private String email;

    private String name;

    private String password;

    private UserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(userId, that.userId) && Objects.equals(email, that.email) && Objects.equals(name, that.name) && Objects.equals(password, that.password) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, name, password, role);
    }
}
