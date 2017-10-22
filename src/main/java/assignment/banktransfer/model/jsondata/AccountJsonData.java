package assignment.banktransfer.model.jsondata;

import java.math.BigDecimal;

/**
 * This class represents the account data received from the body of POST request.
 *
 * {
 *    "name":"test",
 *    "balance":50
 * }
 */
public class AccountJsonData {

    private String name;
    private BigDecimal balance;

    // Empty constructor is needed for JSON (de)serialization
    public AccountJsonData() {

    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountJsonData(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

}
