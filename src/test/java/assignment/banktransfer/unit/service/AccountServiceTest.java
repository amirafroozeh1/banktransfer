package assignment.banktransfer.unit.service;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.jsondata.AccountJsonData;
import assignment.banktransfer.repository.AccountRepository;
import assignment.banktransfer.service.AccountServiceImpl;
import assignment.banktransfer.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService  = new AccountServiceImpl();
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private Account account;
    @Mock
    private Set<Account> accounts;

    private UUID accountId;
    private String name;
    private BigDecimal balance;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        accountId = UUID.randomUUID();
        name = "test";
        balance = new BigDecimal("1000.5");
    }

    @Test
    public void findByIdTest() throws Exception {
        // Arrange
        when(accountRepository.findById(accountId)).thenReturn(account);
        // Act
        Account retrievedAccount = accountService.findById(accountId);
        // Assert
        assertThat(retrievedAccount, is(equalTo(account)));
    }

    @Test
    public void addAccountTest() throws Exception {
        // Arrange
        Account account = new Account(name, balance);
        doNothing().when(accountRepository).addAccount(account);
        // Act
        accountService.addAccount(account);
        // Assert
        verify(accountRepository).addAccount(account);
    }

    @Test
    public void findByNameTest() throws Exception {
        // Arrange
        when(accountRepository.findByName(name)).thenReturn(accounts);
        // Act
        Set<Account> retrievedAccounts = accountService.findByName(name);
        // Assert
        assertThat(retrievedAccounts, is(equalTo(accounts)));
    }

    @Test
    public void isValidTest() throws Exception {
        AccountJsonData accountJsonData = new AccountJsonData("Test", BigDecimal.TEN);
        assertThat(accountService.isValid(accountJsonData), is(equalTo(true)));
    }

}