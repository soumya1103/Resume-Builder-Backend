package com.project.ResumeBuilder.indto;

import com.project.ResumeBuilder.enums.UserRole;

import java.util.Objects;

public class UserRequest {

    private Long userId;

    private String userEmail;

    private String userName;

    private String userPassword;

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
        UserRequest that = (UserRequest) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userEmail, that.userEmail) && Objects.equals(userName, that.userName) && Objects.equals(userPassword, that.userPassword) && userRole == that.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userEmail, userName, userPassword, userRole);
    }
}
