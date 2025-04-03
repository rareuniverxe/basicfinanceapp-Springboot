package com.financeapp;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    final private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category) {
        return transactionService.getTransactions(type, category);
    }

    @GetMapping("/transactions/summary")
    public Map<String, Double> getTransactionSummary() {
        return transactionService.getTransactionSummary();
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable int id) {
        if (transactionService.deleteTransaction(id)) {
            return ResponseEntity.ok("deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
        }
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable int id, @RequestBody Transaction updatedTransaction) {
        if (transactionService.updateTransaction(id, updatedTransaction)) {
            return ResponseEntity.ok("Transaction updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> addTransaction(@Valid @RequestBody Transaction transaction, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body("Validation error: " + result.getAllErrors().get(0).getDefaultMessage());
        }
        transactionService.addTransaction(transaction);
        return ResponseEntity.ok("Transaction saved!");
    }
}
