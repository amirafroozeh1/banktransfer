package assignment.banktransfer.service;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.jsondata.AccountJsonData;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public interface AccountService {

    Account findById(UUID accountId);

    void addAccount(Account account);

    Set<Account> findByName(String name);

    boolean isValid(AccountJsonData accountJsonData);

    void deleteAll();

    BigDecimal totalBalance();
}
