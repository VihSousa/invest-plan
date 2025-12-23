package br.com.vihsousa.invest_plan.dto.user;

// DTO for sending user data in responses
// What the application sends back to the user

public record UserResponseDTO (

    Long id,
    String name,
    String email
    
) {}