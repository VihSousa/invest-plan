package br.com.VihSousa.invest_plan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO for creating a new user with validation constraints
// What the user must send

public record UserCreateDTO (
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
    String name,

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "O formato do e-mail é inválido")
    String email,

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    String password
) {}
