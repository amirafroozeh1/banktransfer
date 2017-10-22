package assignment.banktransfer.service;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.jsondata.AccountJsonData;
import assignment.banktransfer.repository.AccountRepository;
import assignment.banktransfer.util.exception.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * The AccountServiceImpl provides an implementation of AccountService, by adding a new account, finding
 * an account by id, finding by name, delete all and checking exist of an account.
 *
 * @author  Amir Afroozeh
 * @version 1.0
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findById(UUID accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public void addAccount(Account account) {
        accountRepository.addAccount(account);
    }

    @Override
    public Set<Account> findByName(String name) {
        return accountRepository.findByName(name);
    }

    @Override
    public boolean isValid(AccountJsonData accountJsonData) {
        if (accountJsonData == null || accountJsonData.getName() == null || accountJsonData.getBalance() == null) {
            return false;
        }
        return Validation.isBalancePositive(accountJsonData.getBalance()) && Validation.isValidName(accountJsonData.getName());
    }

    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
    }

    @Override
    public BigDecimal totalBalance() {
        return accountRepository.totalBalance();
    }

}
