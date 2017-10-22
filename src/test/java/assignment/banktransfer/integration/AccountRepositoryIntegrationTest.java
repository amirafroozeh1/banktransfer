package assignment.banktransfer.integration;

import assignment.banktransfer.AppConfig;
import assignment.banktransfer.model.Account;
import assignment.banktransfer.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AccountRepositoryIntegrationTest {

    @Autowired
    AccountRepository accountRepository;
    Account accountA;
    Account accountB;
    Account accountC;
    Account accountD;
    Account accountE;

    @Before
    public void setup() {
        accountRepository.deleteAll();
        accountA = new Account("nameA", new BigDecimal(100));
        accountB = new Account("nameA", new BigDecimal(100));
        accountC = new Account("nameC", new BigDecimal(100));
        accountD = new Account("nameD", new BigDecimal(100));
        accountE = new Account("nameE", new BigDecimal(100));
        accountRepository.addAccount(accountA);
        accountRepository.addAccount(accountB);
        accountRepository.addAccount(accountC);
        accountRepository.addAccount(accountD);
        accountRepository.addAccount(accountE);
    }

    @Test
    public void totalBalanceTest() {
        Assert.assertEquals(accountRepository.totalBalance(), new BigDecimal(500));
    }

    @Test
    public void deleteAllTest() {
        accountRepository.deleteAll();
        Assert.assertNull(accountRepository.findById(accountA.getAccountId()));
        Assert.assertNull(accountRepository.findById(accountB.getAccountId()));
        Assert.assertNull(accountRepository.findById(accountC.getAccountId()));
        Assert.assertNull(accountRepository.findById(accountD.getAccountId()));
        Assert.assertNull(accountRepository.findById(accountE.getAccountId()));
    }

    @Test
    public void findByNameTest() {
        Set<Account> accounts = accountRepository.findByName("nameA");
        Assert.assertEquals(accounts.size(), 2);
        Set<UUID> expectedSet = new HashSet<>(Arrays.asList(accountA.getAccountId(), accountB.getAccountId()));
        Assert.assertEquals(accounts.stream().map(Account::getAccountId).collect(Collectors.toSet()), expectedSet);
    }
}
