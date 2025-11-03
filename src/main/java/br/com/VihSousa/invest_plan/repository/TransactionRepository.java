package br.com.VihSousa.invest_plan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.VihSousa.invest_plan.model.Transaction;

// Defines the repository interface
// Responsible ONLY for saving, finding, updating, and deleting Transactions
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Creates a query "SELECT * FROM transactions WHERE user_id = ?"
    List<Transaction> findByUserId(Long userId);
}
