package com.hsbc.trans_demo.util;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Validator class for validating transaction data.
 */
@Component
public class TransactionValidator {

    /**
     * Validates the amount of a transaction.
     * @param amount The amount to be validated.
     * @return true if the amount is greater than zero, false otherwise.
     */
    public boolean validateAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }
}