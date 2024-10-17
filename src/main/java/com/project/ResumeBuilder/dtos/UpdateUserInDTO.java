package com.project.ResumeBuilder.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserInDTO {

    @Pattern(
            regexp = "^[a-zA-Z\\s'-]{2,50}$",
            message = "A valid name is mandatory"
    )
    @NotBlank(message = "A valid name is mandatory")
    private String name;

    @Email(message = "Valid Email not found")
    @NotBlank(message = "Valid Email not found")
    @Pattern(
            regexp = "^[\\w.%+-]+@gmail\\.com$",
            message = "Valid Email not found"
    )
    private String email;

    private String address;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;

    private String gender;
}
