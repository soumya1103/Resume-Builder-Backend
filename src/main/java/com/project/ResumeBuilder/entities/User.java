package com.project.ResumeBuilder.entities;


import com.project.ResumeBuilder.enums.UserRole;
import jakarta.persistence.*;

import java.util.Objects;

@Table(name="users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userEmail;

    private String userName;

    private String userPassword;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userEmail, user.userEmail) && Objects.equals(userName, user.userName) && Objects.equals(userPassword, user.userPassword) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userEmail, userName, userPassword, userRole);
    }
}
