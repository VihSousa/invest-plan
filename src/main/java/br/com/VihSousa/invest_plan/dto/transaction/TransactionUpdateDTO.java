package br.com.VihSousa.invest_plan.dto.transaction;

public record TransactionUpdateDTO (

    String description,
    java.math.BigDecimal amount,
    Long categoryId

) {}
