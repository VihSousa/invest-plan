package br.com.vihsousa.invest_plan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante!

import br.com.vihsousa.invest_plan.dto.transaction.TransactionCreateDTO;
import br.com.vihsousa.invest_plan.dto.transaction.TransactionResponseDTO;
import br.com.vihsousa.invest_plan.model.User;
import br.com.vihsousa.invest_plan.model.Category;
import br.com.vihsousa.invest_plan.model.Transaction;
import br.com.vihsousa.invest_plan.repository.CategoryRepository;
import br.com.vihsousa.invest_plan.repository.TransactionRepository;
import br.com.vihsousa.invest_plan.repository.UserRepository;
import br.com.vihsousa.invest_plan.service.exception.ResourceNotFoundException;
import br.com.vihsousa.invest_plan.service.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@SuppressWarnings("null")
public class TransactionService {

    // The service needs ALL repositories it will interact with.
    private final TransactionRepository transactionRepository;
    private final UserRepository        userRepository;
    private final CategoryRepository    categoryRepository;

    /* @Transactional:
    * Ensures  that  all database operations within this method are treated as
    * a single "atomic transaction". If anything goes wrong (e.g.,insufficient
    * funds), Spring rolls back ALL changes made.
    */
    @Transactional
    public TransactionResponseDTO registerTransaction(long userId, TransactionCreateDTO dto) {
        
        // Fetches the related entities or throws an error if they don't exist.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        // Update the user's balance.
        BigDecimal amount = dto.amount();
        switch (dto.type()) {
            case INCOME:
                user.setBalance(user.getBalance().add(amount));
                break;
            case EXPENSE:
                // Checks if the balance is sufficient before subtracting
                if (user.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException("Insufficient funds!");
                }
                user.setBalance(user.getBalance().subtract(amount));
                break;
        }

        // Saves the user with the updated balance.
        userRepository.save(user);

        // Creates the actual Transaction ENTITY to save in the database
        Transaction newTransaction = new Transaction();
        newTransaction.setDescription(dto.description());
        newTransaction.setAmount(dto.amount());
        newTransaction.setType(dto.type());
        newTransaction.setUser(user);
        newTransaction.setCategory(category);

        Transaction savedTransaction = transactionRepository.save(newTransaction);

        // Entity to DTO conversion before returning the response
        return toResponseDTO(savedTransaction);

    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> findByUserId(long userId) {
        // First, verify if the user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found!");
        }

        // Fetch transactions for the user
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        // Convert entities to DTOs and return
        return transactions.stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
    }

    // Helper method to convert Transaction entity to TransactionResponseDTO
    private TransactionResponseDTO toResponseDTO(Transaction entity) {

        return new TransactionResponseDTO(
            entity.getId(),
            entity.getUser().getId(),
            entity.getCategory().getId(),
            entity.getDescription(),
            entity.getAmount(),
            entity.getType(),
            entity.getDate()
        );
    }

}

