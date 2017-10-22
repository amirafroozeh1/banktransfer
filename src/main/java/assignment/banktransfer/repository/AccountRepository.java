package assignment.banktransfer.repository;

import assignment.banktransfer.model.Account;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public interface AccountRepository {

    Account findById(UUID accountId);

    void addAccount(Account account);

    Set<Account> findByName(String name);

    void deleteAll();

    BigDecimal totalBalance();
}
