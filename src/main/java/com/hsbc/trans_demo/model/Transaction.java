package com.hsbc.trans_demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a financial transaction.
 */
public class Transaction {
    private String id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    /**
     * Constructor for creating a new transaction.
     * @param id The unique identifier of the transaction.
     * @param description A brief description of the transaction.
     * @param amount The amount involved in the transaction.
     * @param timestamp The time when the transaction occurred.
     */
    public Transaction(String id, String description, BigDecimal amount, LocalDateTime timestamp) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}