package br.com.VihSousa.invest_plan.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDTO (
    
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, message = "The category name must contain at least 3 characters")
    String name

) {}
