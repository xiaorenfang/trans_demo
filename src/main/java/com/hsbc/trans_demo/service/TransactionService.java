package com.hsbc.trans_demo.service;

import com.hsbc.trans_demo.model.Transaction;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing transactions.
 */
@Service
public class TransactionService {
    private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    /**
     * Retrieves all transactions.
     * @return A list of all transactions.
     */
    @Cacheable("transactions")
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }

    /**
     * Creates a new transaction.
     * @param description A brief description of the transaction.
     * @param amount The amount involved in the transaction.
     * @return The newly created transaction.
     * @throws IllegalArgumentException if a duplicate transaction ID is detected.
     */
    public Transaction createTransaction(String description, BigDecimal amount) {
        String id = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(id, description, amount, LocalDateTime.now());
        if (transactions.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate transaction ID");
        }
        transactions.put(id, transaction);
        return transaction;
    }

    /**
     * Deletes a transaction by its ID.
     * @param id The ID of the transaction to be deleted.
     * @throws NoSuchElementException if the transaction with the given ID is not found.
     */
    @CacheEvict(value = "transactions", allEntries = true)
    public void deleteTransaction(String id) {
        if (!transactions.containsKey(id)) {
            throw new NoSuchElementException("Transaction with ID " + id + " not found");
        }
        transactions.remove(id);
    }

    /**
     * Modifies an existing transaction.
     * @param id The ID of the transaction to be modified.
     * @param description The new description of the transaction.
     * @param amount The new amount of the transaction.
     * @return The modified transaction.
     * @throws NoSuchElementException if the transaction with the given ID is not found.
     */
    @CacheEvict(value = "transactions", allEntries = true)
    public Transaction modifyTransaction(String id, String description, BigDecimal amount) {
        Transaction transaction = transactions.get(id);
        if (transaction == null) {
            throw new NoSuchElementException("Transaction with ID " + id + " not found");
        }
        transaction.setDescription(description);
        transaction.setAmount(amount);
        return transaction;
    }
}