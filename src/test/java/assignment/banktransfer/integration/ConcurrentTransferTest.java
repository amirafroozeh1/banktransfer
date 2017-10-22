package assignment.banktransfer.integration;

import assignment.banktransfer.AppConfig;
import assignment.banktransfer.model.Account;
import assignment.banktransfer.service.AccountService;
import assignment.banktransfer.service.TransferService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ConcurrentTransferTest {

    private List<Account> accounts;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    private int numAccounts = 1000;
    private int initialBalance = 1000;
    private int numTransfers = 10000;


    @Before
    public void init() {
        accountService.deleteAll();
        transferService.deleteAll();
        accounts = new ArrayList<>();
        for (int i = 0; i < numAccounts; i++) {
            Account account = new Account("nameA", new BigDecimal(initialBalance));
            accounts.add(account);
            accountService.addAccount(account);
        }
    }

    @Test
    public void test() {

        Assert.assertEquals(accountService.totalBalance().intValue(), numAccounts * initialBalance);
        Random random = new Random();

        CountDownLatch latch = new CountDownLatch(numTransfers);

        for (int i = 0; i < numTransfers; i++) {
            int fromIndex = random.nextInt(numAccounts);
            int toIndex = random.nextInt(numAccounts);
            Account from = accounts.get(fromIndex);
            Account to = accounts.get(toIndex);

            int amount = random.nextInt(initialBalance);

            new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                transferService.executeTransfer(from, to, new BigDecimal(amount), "test");
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(accountService.totalBalance().intValue(), numAccounts * initialBalance);
    }
}
