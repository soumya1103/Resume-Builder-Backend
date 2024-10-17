package com.project.ResumeBuilder.entities;


import com.project.ResumeBuilder.enums.Gender;
import com.project.ResumeBuilder.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", insertable=false, updatable=false)
    private Long userId;

    private String email;

    private String name;

    private String password;

    private String address;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(userId, users.userId) && Objects.equals(email, users.email) && Objects.equals(name, users.name) && Objects.equals(password, users.password) && Objects.equals(address, users.address) && Objects.equals(dob, users.dob) && gender == users.gender && role == users.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, name, password, address, dob, gender, role);
    }
}
