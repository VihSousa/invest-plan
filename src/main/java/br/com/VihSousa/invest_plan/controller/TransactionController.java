package br.com.VihSousa.invest_plan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.VihSousa.invest_plan.dto.transaction.TransactionCreateDTO;
import br.com.VihSousa.invest_plan.dto.transaction.TransactionResponseDTO;
import br.com.VihSousa.invest_plan.service.TransactionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    // ============================== ENDPOINTS ============================== \\

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @PathVariable long userId,
            @RequestBody TransactionCreateDTO transactionCreateDTO
    ) {
        TransactionResponseDTO savedTransaction = transactionService.registerTransaction(userId, transactionCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUserId(@PathVariable long userId) {
        List<TransactionResponseDTO> transactions = transactionService.findByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

}
