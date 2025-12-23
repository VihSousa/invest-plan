package br.com.vihsousa.invest_plan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vihsousa.invest_plan.dto.transaction.TransactionCreateDTO;
import br.com.vihsousa.invest_plan.dto.transaction.TransactionResponseDTO;
import br.com.vihsousa.invest_plan.model.Category;
import br.com.vihsousa.invest_plan.model.Transaction;
import br.com.vihsousa.invest_plan.model.TransactionType;
import br.com.vihsousa.invest_plan.model.User;
import br.com.vihsousa.invest_plan.repository.CategoryRepository;
import br.com.vihsousa.invest_plan.repository.TransactionRepository;
import br.com.vihsousa.invest_plan.repository.UserRepository;
import br.com.vihsousa.invest_plan.service.exception.InsufficientFundsException;
import br.com.vihsousa.invest_plan.service.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    // =========================================================================
    //                              SUCCESS PATH TESTS                                 
    // =========================================================================

    @SuppressWarnings({ "null", "unused" })
    @Test
    @DisplayName("Should create EXPENSE transaction successfully and decrease balance")
    void shouldCreateExpenseTransactionSuccessfully() {

        // Arrange
        User user = new User();
        user.setId(1L);
        user.setBalance(new BigDecimal("100.00"));

        Category category = new Category();
        category.setId(1L);

        TransactionCreateDTO dto = new TransactionCreateDTO(
            1L, "Lunch", new BigDecimal("50.00"), TransactionType.EXPENSE
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TransactionResponseDTO response = transactionService.registerTransaction(1L, dto);

        // Assert
        assertEquals(50.00, user.getBalance().doubleValue()); // 100 - 50 = 50
        verify(transactionRepository, times(1)).save(any());
    }

    @SuppressWarnings({ "null" })
    @Test
    @DisplayName("Should create INCOME transaction successfully and increase balance")
    void shouldCreateIncomeTransactionSuccessfully() {

        // Arrange
        User user = new User();
        user.setId(1L);
        user.setBalance(new BigDecimal("100.00"));

        Category category = new Category();
        category.setId(1L);

        // Create an INCOME transaction
        TransactionCreateDTO dto = new TransactionCreateDTO(
            1L, "Wage", new BigDecimal("50.00"), TransactionType.INCOME
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        transactionService.registerTransaction(1L, dto);

        // Assert
        assertEquals(150.00, user.getBalance().doubleValue());
    }

    // =========================================================================
    //                              ERROR PATH TESTS                                 
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should throw exception when balance is insufficient for expense")
    void shouldThrowExceptionWhenInsufficientFunds() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setBalance(new BigDecimal("10.00"));

        Category category = new Category();
        category.setId(1L);

        // Try to make an EXPENSE greater than the balance
        TransactionCreateDTO dto = new TransactionCreateDTO(
            1L, "Lunch Caro", new BigDecimal("50.00"), TransactionType.EXPENSE
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act and Assert
        assertThrows(InsufficientFundsException.class, () -> transactionService.registerTransaction(1L, dto));

        // Make sure the balance did NOT change and it did NOT save to the database
        assertEquals(10.00, user.getBalance().doubleValue());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when User not found")
    void shouldThrowExceptionWhenUserNotFound() {
        TransactionCreateDTO dto = new TransactionCreateDTO(
            1L, "Teste", BigDecimal.TEN, TransactionType.EXPENSE
        );

        // Did not find user
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transactionService.registerTransaction(99L, dto));
    }
}