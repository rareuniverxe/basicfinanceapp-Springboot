package com.financeapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactions(String type, String category) {
        List<Transaction> transactions;

        if (type != null && category != null) {
            transactions = transactionRepository.findByTypeAndCategory(type, category);
        } else if (type != null) {
            transactions = transactionRepository.findByType(type);
        } else if (category != null) {
            transactions = transactionRepository.findByCategory(category);
        } else {
            transactions = transactionRepository.findAll();
        }

        return transactions;
    }

    public Map<String, Double> getTransactionSummary() {
        List<Transaction> transactions = transactionRepository.findAll();
        double income = 0;
        double expense = 0;

        for (Transaction t : transactions) {
            if ("income".equalsIgnoreCase(t.getType())) {
                income += t.getAmount();
            }
            if ("expense".equalsIgnoreCase(t.getType())) {
                expense += t.getAmount();
            }
        }

        Map<String, Double> summary = new HashMap<>();
        summary.put("income", income);
        summary.put("expense", expense);
        summary.put("net", income - expense);

        return summary;
    }

    public boolean deleteTransaction(int id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTransaction(int id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        if (existingTransaction != null) {
            existingTransaction.setAmount(updatedTransaction.getAmount());
            existingTransaction.setCategory(updatedTransaction.getCategory());
            existingTransaction.setType(updatedTransaction.getType());
            existingTransaction.setTimestamp(updatedTransaction.getTimestamp());
            // Add more fields here if needed, like date, description, etc.

            transactionRepository.save(existingTransaction);
            return true;
        } else {
            return false;
        }
    }

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
