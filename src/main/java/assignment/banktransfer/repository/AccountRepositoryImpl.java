package assignment.banktransfer.repository;


import assignment.banktransfer.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * The AccountRepositoryImpl provides an implementation of AccountRepository, by adding a new account, finding
 * an account by id, finding by name, checking exist of an account and delete all accounts.
 *
 * @author  Amir Afroozeh
 * @version 1.0
 */
@Component
public class AccountRepositoryImpl implements AccountRepository {

    private Map<UUID, Account> accounts;

    public AccountRepositoryImpl() {
        this.accounts = new HashMap<>();
    }

    @Override
    public Account findById(UUID accountId) {
        return accounts.get(accountId);
    }

    @Override
    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    @Override
    public Set<Account> findByName(String name) {
        Set<Account> accountList = new HashSet<>();
        for (Map.Entry<UUID, Account> entry : accounts.entrySet()) {
            if (entry.getValue().getName().equals(name))
                accountList.add(findById(entry.getKey()));
        }
        return accountList;
    }

    @Override
    public void deleteAll() {
        accounts.clear();
    }

    @Override
    public BigDecimal totalBalance() {
        return accounts.values().stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
