package br.com.vihsousa.invest_plan.dto.transaction;

import br.com.vihsousa.invest_plan.model.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionCreateDTO (

    @NotNull(message = "The category ID is required")
    Long categoryId, // The client only needs to send the category ID

    @NotBlank(message = "Description cannot be blank")
    String description,

    @NotNull(message = "The amount cannot be null")
    @Positive(message = "The amount must be positive")
    BigDecimal amount,

    @NotNull(message = "The transaction type is required")
    TransactionType type
    
) {}

