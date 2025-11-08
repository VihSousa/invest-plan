package br.com.VihSousa.invest_plan.dto;

import java.math.BigDecimal;

public record TransactionResponseDTO(
    Long id,
    Long userId,
    Long categoryId,
    String description,
    BigDecimal amount
) {}
