package br.com.vihsousa.invest_plan.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateDTO(
    
    @NotBlank(message = "Name is required")
    String name
    
) {} 
