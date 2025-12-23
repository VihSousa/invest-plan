package br.com.vihsousa.invest_plan.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.vihsousa.invest_plan.model.TransactionType;

public record TransactionResponseDTO(

    Long id,
    Long userId,
    Long categoryId,
    String description,
    BigDecimal amount,
    TransactionType type,
    LocalDateTime date
    
) {}
