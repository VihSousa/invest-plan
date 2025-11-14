package br.com.VihSousa.invest_plan.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO for creating a new user with validation constraints
// What the user must send

public record UserCreateDTO (

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "The name must contain at least 3 characters")
    String name,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "The email format is invalid")
    String email,

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "The password must contain at least 8 characters")
    String password
    
) {}
