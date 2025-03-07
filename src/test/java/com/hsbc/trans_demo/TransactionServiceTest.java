package com.hsbc.trans_demo;

import com.hsbc.trans_demo.model.Transaction;
import com.hsbc.trans_demo.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TransactionService class.
 */
class TransactionServiceTest {
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        transactionService = context.getBean(TransactionService.class);
    }

    /**
     * Tests the creation of a new transaction.
     */
    @Test
    void testCreateTransaction() {
        String description = "Test Transaction";
        BigDecimal amount = BigDecimal.valueOf(100.0);
        Transaction transaction = transactionService.createTransaction(description, amount);
        assertNotNull(transaction.getId());
        assertEquals(description, transaction.getDescription());
        assertEquals(amount, transaction.getAmount());
    }

    /**
     * Tests the deletion of a transaction.
     */
    @Test
    void testDeleteTransaction() {
        String description = "Test Transaction";
        BigDecimal amount = BigDecimal.valueOf(100.0);
        Transaction transaction = transactionService.createTransaction(description, amount);
        assertDoesNotThrow(() -> transactionService.deleteTransaction(transaction.getId()));
        assertThrows(NoSuchElementException.class, () -> transactionService.deleteTransaction(transaction.getId()));
    }

    /**
     * Tests the modification of a transaction.
     */
    @Test
    void testModifyTransaction() {
        String description = "Test Transaction";
        BigDecimal amount = BigDecimal.valueOf(100.0);
        Transaction transaction = transactionService.createTransaction(description, amount);

        String newDescription = "Updated Transaction";
        BigDecimal newAmount = BigDecimal.valueOf(200.0);
        Transaction updatedTransaction = transactionService.modifyTransaction(transaction.getId(), newDescription, newAmount);

        assertEquals(newDescription, updatedTransaction.getDescription());
        assertEquals(newAmount, updatedTransaction.getAmount());
    }

    /**
     * Tests the retrieval of all transactions.
     */
    @Test
    void testGetAllTransactions() {
        String description = "Test Transaction";
        BigDecimal amount = BigDecimal.valueOf(100.0);
        transactionService.createTransaction(description, amount);

        List<Transaction> transactions = transactionService.getAllTransactions();
        assertFalse(transactions.isEmpty());
    }

    @Configuration
    @EnableCaching
    static class TestConfig {
        @Bean
        public TransactionService transactionService() {
            return new TransactionService();
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("transactions");
        }
    }
}