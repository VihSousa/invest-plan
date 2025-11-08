package br.com.VihSousa.invest_plan.dto;

import java.math.BigDecimal;

import br.com.VihSousa.invest_plan.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionCreateDTO (
    @NotBlank(message = "A descrição não pode estar em branco")
    String description,

    @NotNull(message = "O valor não pode ser nulo")
    @Positive(message = "O valor deve ser positivo")
    BigDecimal amount,

    @NotNull(message = "O tipo da transação é obrigatório")
    TransactionType type,

    @NotNull(message = "O ID da categoria é obrigatório")
    Long categoryId // O cliente só precisa enviar o ID da categoria
) {}

