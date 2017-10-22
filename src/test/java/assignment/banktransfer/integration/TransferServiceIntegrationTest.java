package assignment.banktransfer.integration;

import assignment.banktransfer.AppConfig;
import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.Transfer;
import assignment.banktransfer.service.AccountService;
import assignment.banktransfer.service.TransferService;
import assignment.banktransfer.model.TransferStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TransferServiceIntegrationTest {

    private Account from;
    private Account to;

    @Autowired
    TransferService transferService;

    @Autowired
    private AccountService accountService;

    @Before
    public void setUp() {
        transferService.deleteAll();
        accountService.deleteAll();
        from = new Account("testA", new BigDecimal(100.1201));
        to = new Account("testB", new BigDecimal(75));
    }

    @Test
    public void successfulTransferTest() {
        Transfer transfer = transferService.executeTransfer(from, to, BigDecimal.TEN, "this is a test description");
        assertEquals(transfer.getStatus(), TransferStatus.SUCCESS);
    }

    @Test
    public void unSuccessfulTransferTest() {
        Transfer transfer = transferService.executeTransfer(from, to, new BigDecimal(-20), "this is a test description");
        assertEquals(transfer.getStatus(), TransferStatus.FAILURE);
    }

    @Test
    public void removeAllTest() {
        Transfer transfer = transferService.executeTransfer(from, to, BigDecimal.TEN, "this is a test description");
        assertEquals(transfer.getStatus(), TransferStatus.SUCCESS);
    }

}
