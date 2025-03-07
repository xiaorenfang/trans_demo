package com.hsbc.trans_demo.controller;

import com.hsbc.trans_demo.model.Transaction;
import com.hsbc.trans_demo.service.TransactionService;
import com.hsbc.trans_demo.util.TransactionValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller class for handling transaction-related API requests.
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionValidator transactionValidator;

    /**
     * Constructor for the TransactionController.
     * @param transactionService The service for managing transactions.
     * @param transactionValidator The validator for transaction data.
     */
    public TransactionController(TransactionService transactionService, TransactionValidator transactionValidator) {
        this.transactionService = transactionService;
        this.transactionValidator = transactionValidator;
    }

    /**
     * Retrieves all transactions.
     * @return A response entity containing a list of all transactions and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    /**
     * Creates a new transaction.
     * @param description A brief description of the transaction.
     * @param amount The amount involved in the transaction.
     * @return A response entity containing the newly created transaction and HTTP status CREATED,
     * or HTTP status BAD_REQUEST if the input is invalid.
     */
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestParam String description, @RequestParam BigDecimal amount) {
        if (!transactionValidator.validateAmount(amount)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Transaction transaction = transactionService.createTransaction(description, amount);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a transaction by its ID.
     * @param id The ID of the transaction to be deleted.
     * @return A response entity with HTTP status NO_CONTENT if the deletion is successful,
     * or HTTP status NOT_FOUND if the transaction is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String id) {
        try {
            transactionService.deleteTransaction(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Modifies an existing transaction.
     * @param id The ID of the transaction to be modified.
     * @param description The new description of the transaction.
     * @param amount The new amount of the transaction.
     * @return A response entity containing the modified transaction and HTTP status OK,
     * or HTTP status NOT_FOUND if the transaction is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> modifyTransaction(@PathVariable String id, @RequestParam String description, @RequestParam BigDecimal amount) {
        if (!transactionValidator.validateAmount(amount)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Transaction transaction = transactionService.modifyTransaction(id, description, amount);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}