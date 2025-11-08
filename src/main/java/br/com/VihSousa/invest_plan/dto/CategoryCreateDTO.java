package br.com.VihSousa.invest_plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDTO (
    
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 3, message = "O nome da categoria deve ter pelo menos 3 caracteres")
    String name

) {}
