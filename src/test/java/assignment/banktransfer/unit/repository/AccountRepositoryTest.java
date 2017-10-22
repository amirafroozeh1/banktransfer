package assignment.banktransfer.unit.repository;

import assignment.banktransfer.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AccountRepositoryTest {

    private Map<UUID, Account> accounts;
    private UUID accountId;
    private Account account;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        accountId = UUID.randomUUID();
        accounts = new HashMap<>();
        account = new Account();
    }

    @Test
    public void findByIdTest() throws Exception {

        Map<UUID, Account> spy = spy(accounts);

        when(spy.get(accountId)).thenReturn(account);

        assertEquals(account, spy.get(accountId));
    }

    @Test
    public void accountExistsTest() throws Exception {

        Map<UUID, Account> spy = spy(accounts);

        when(spy.containsKey(accountId)).thenReturn(true);

        assertEquals(true, spy.containsKey(accountId));
    }
}