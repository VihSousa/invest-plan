package br.com.VihSousa.invest_plan.dto;

// DTO for sending user data in responses
// What the application sends back to the user

public record UserResponseDTO (
    Long id,
    String name,
    String email
) {}