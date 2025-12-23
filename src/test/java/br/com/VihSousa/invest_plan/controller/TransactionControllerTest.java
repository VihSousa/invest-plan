package br.com.vihsousa.invest_plan.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vihsousa.invest_plan.dto.transaction.TransactionCreateDTO;
import br.com.vihsousa.invest_plan.dto.transaction.TransactionResponseDTO;
import br.com.vihsousa.invest_plan.model.TransactionType;
import br.com.vihsousa.invest_plan.service.TransactionService;
import br.com.vihsousa.invest_plan.service.exception.InsufficientFundsException;
import br.com.vihsousa.invest_plan.service.exception.ResourceNotFoundException;

import br.com.vihsousa.invest_plan.service.TokenService;
import br.com.vihsousa.invest_plan.repository.UserRepository;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    // =========================================================================
    //                      SUCCESS PATH TESTS                       
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 201 Created when transaction registered")
    void shouldReturnCreatedWhenTransactionRegistered() throws Exception {
        TransactionCreateDTO request = new TransactionCreateDTO(1L, "Lunch", new BigDecimal("50.00"), TransactionType.EXPENSE);
        
        TransactionResponseDTO response = new TransactionResponseDTO(
            1L, 1L, 1L, "Lunch", new BigDecimal("50.00"), TransactionType.EXPENSE, LocalDateTime.now()
        );

        when(transactionService.registerTransaction(eq(1L), any())).thenReturn(response);

        // ATTENTION URL: /users/{userId}/transactions
        mockMvc.perform(post("/users/{userId}/transactions", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(50.00));
    }

    // =========================================================================
    //                       ERROR PATH TESTS                        
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 422 Unprocessable Entity when insufficient funds")
    void shouldReturnUnprocessableEntityWhenNoFunds() throws Exception {
        TransactionCreateDTO request = new TransactionCreateDTO(1L, "Caro", new BigDecimal("5000.00"), TransactionType.EXPENSE);

        when(transactionService.registerTransaction(eq(1L), any()))
            .thenThrow(new InsufficientFundsException("Sem saldo"));

        mockMvc.perform(post("/users/{userId}/transactions", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity()) // 422
                .andExpect(jsonPath("$.error").value("Insufficient Funds"));
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 400 Bad Request when amount is negative")
    void shouldReturnBadRequestWhenNegativeAmount() throws Exception {
        // Invalid DTO (@Positive validation fails here)
        TransactionCreateDTO invalidRequest = new TransactionCreateDTO(1L, "Error", new BigDecimal("-10.00"), TransactionType.EXPENSE);

        mockMvc.perform(post("/users/{userId}/transactions", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 404 Not Found when User or Category missing")
    void shouldReturnNotFoundWhenMissingEntities() throws Exception {
        TransactionCreateDTO request = new TransactionCreateDTO(99L, "Test", BigDecimal.TEN, TransactionType.EXPENSE);

        when(transactionService.registerTransaction(eq(1L), any()))
            .thenThrow(new ResourceNotFoundException("Category not found"));

        mockMvc.perform(post("/users/{userId}/transactions", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}