package br.com.VihSousa.invest_plan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante!

import br.com.VihSousa.invest_plan.model.Category;
import br.com.VihSousa.invest_plan.model.Transaction;
import br.com.VihSousa.invest_plan.model.User;
import br.com.VihSousa.invest_plan.repository.CategoryRepository;
import br.com.VihSousa.invest_plan.repository.TransactionRepository;
import br.com.VihSousa.invest_plan.repository.UserRepository;
import br.com.VihSousa.invest_plan.service.exception.ResourceNotFoundException;
import br.com.VihSousa.invest_plan.service.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    // The service needs ALL repositories it will interact with.
    private final TransactionRepository transactionRepository;
    private final UserRepository        userRepository;
    private final CategoryRepository    categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository        = userRepository;
        this.categoryRepository    = categoryRepository;
    }

    /* @Transactional:
    * Ensures  that  all database operations within this method are treated as
    * a single "atomic transaction". If anything goes wrong (e.g.,insufficient
    * funds), Spring rolls back ALL changes made.
    */
    @Transactional
    public Transaction registerTransaction(Long userId, Transaction dataOfTransaction) {
        
        // Fetches the related entities or throws an error if they don't exist.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Category category = categoryRepository.findById(dataOfTransaction.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        // Update the user's balance.
        BigDecimal value = dataOfTransaction.getValue();
        switch (dataOfTransaction.getType()) {
            case INCOME:
                user.setBalance(user.getBalance().add(value));
                break;
            case EXPENSE:
                // Checks if the balance is sufficient before subtracting
                if (user.getBalance().compareTo(value) < 0) {
                    throw new InsufficientFundsException("Insufficient funds!");
                }
                user.setBalance(user.getBalance().subtract(value));
                break;
        }

        // Saves the user with the updated balance.
        userRepository.save(user);

        // Prepares and saves the new transaction.
        dataOfTransaction.setUser(user);
        dataOfTransaction.setCategory(category);
        dataOfTransaction.setDate(LocalDateTime.now()); // Ensures the date is the server's

        return transactionRepository.save(dataOfTransaction);
    }

    public List<Transaction> findByUserId(Long userId) {
        // First, verify if the user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found!");
        }
        // Calls the new method from the repository
        return transactionRepository.findByUserId(userId);
    }

}
