package assignment.banktransfer.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

import assignment.banktransfer.util.exception.InvalidTransferException;


public class Account {

    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    private UUID accountId;
    private String name;
    private BigDecimal balance;

    public Account(String name, BigDecimal balance) {
        this.accountId = UUID.randomUUID();
        this.name = name;
        this.balance = balance;
    }

    public Account() {

    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Withdraws the given amount from this account
     *
     * @param amount
     * @throws InvalidTransferException if the amount is larger than the current balance
     */
    public synchronized void withdraw(final BigDecimal amount) throws InvalidTransferException {
        validateAmountNotNegative(amount);

        if (balance.compareTo(amount) < 0) {
            logger.error("Cannot withdraw {} from account id {} with balance {}", amount, accountId, balance);
            throw new InvalidTransferException("There is not enough money");
        }

        balance = balance.subtract(amount);
        logger.info("Successfully withdrawn {} from account id {} with balance {}", amount, accountId, balance);
    }

    /**
     * Deposits the given amount into this account
     *
     * @param amount
     */
    public synchronized void deposit(BigDecimal amount) {
        validateAmountNotNegative(amount);
        balance = balance.add(amount);
        logger.info("Successfully deposited {} to account id {}", amount,  accountId);
    }

    private void validateAmountNotNegative(final BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot withdraw negative amount");
        }
    }

}

