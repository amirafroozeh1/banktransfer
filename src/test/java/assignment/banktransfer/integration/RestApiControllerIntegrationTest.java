package assignment.banktransfer.integration;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.Transfer;
import assignment.banktransfer.model.jsondata.AccountJsonData;
import assignment.banktransfer.model.jsondata.TransferJsonData;
import assignment.banktransfer.service.AccountService;
import assignment.banktransfer.service.TransferService;
import assignment.banktransfer.model.TransferStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiControllerIntegrationTest {

    @Autowired
    AccountService accountService;

    @Autowired
    TransferService transferService;

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    private Account from;
    private Account to;

    @Before
    public void setUp() {
        accountService.deleteAll();
        transferService.deleteAll();
        from = new Account("accountA", BigDecimal.TEN);
        to = new Account("accountB", BigDecimal.TEN);
    }

    @Test
    public void createSuccessfulAccountIntegrationTest() throws Exception {

        ResponseEntity<UUID> responseEntity = restTemplate.postForEntity("/api/account",
                new AccountJsonData("account", BigDecimal.TEN),
                UUID.class);
        int status = responseEntity.getStatusCodeValue();
        UUID result = responseEntity.getBody();

        Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
        assertNotNull(result);
    }

    @Test
    public void createUnSuccessfulAccountIntegrationTest() {
        try {
            restTemplate.postForEntity("/api/wrongURL",
                    new AccountJsonData("account", BigDecimal.TEN),
                    UUID.class);
            fail("the method does not throw when I expected it to");
        } catch (Exception e) {

        }
    }

    @Test
    public void findAccountByIdIntegrationTest() {

        accountService.addAccount(from);

        ResponseEntity<Account> responseEntity = restTemplate.getForEntity("/api/account/" + "{id}",
                Account.class,
                from.getAccountId());

        int status = responseEntity.getStatusCodeValue();
        Account result = responseEntity.getBody();

        Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
        Assert.assertEquals(result.getAccountId(), from.getAccountId());
    }

    @Test
    public void findAccountByNameIntegrationTest() {

        accountService.addAccount(from);
        ResponseEntity<Set> responseEntity = restTemplate.getForEntity("/api/account/search/" + "{name}",
                Set.class,
                "accountA");

        int status = responseEntity.getStatusCodeValue();
        Set<Account> result = responseEntity.getBody();

        Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void transferIntegrationTest() {

        accountService.addAccount(from);
        accountService.addAccount(to);

        TransferJsonData transferJsonData = new TransferJsonData(from.getAccountId(), to.getAccountId(), BigDecimal.TEN, "this is a test description");

        ResponseEntity<UUID> responseEntity = restTemplate.postForEntity("/api/transfer", transferJsonData, UUID.class);

        int status = responseEntity.getStatusCodeValue();
        UUID result = responseEntity.getBody();

        Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
        assertNotNull(result);
    }

    @Test
    public void findTransferByIdIntegrationTest() {

        Transfer transfer = new Transfer(from, to, BigDecimal.ONE, TransferStatus.SUCCESS, "this is test description");
        transferService.addTransfer(transfer);

        ResponseEntity<Transfer> responseEntity = restTemplate.getForEntity("/api/transfer/" + "{id}", Transfer.class, transfer.getTransferId());

        int status = responseEntity.getStatusCodeValue();
        Transfer result = responseEntity.getBody();

        Assert.assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
        Assert.assertEquals(result.getTransferId(), transfer.getTransferId());
    }
}