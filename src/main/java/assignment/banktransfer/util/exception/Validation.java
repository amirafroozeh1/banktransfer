package assignment.banktransfer.util.exception;

import java.math.BigDecimal;

public class Validation {

    public static boolean isValidName(String name) {
        return name.matches("^[\\p{L} .'-]+$");
    }

    public static boolean isBalancePositive(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }
}
