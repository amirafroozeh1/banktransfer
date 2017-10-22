package assignment.banktransfer.unit.model;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.util.exception.InvalidTransferException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AccountTest {

    private Account account;

    @Before
    public void setup() {
        account = new Account("testName", BigDecimal.TEN);
    }

    @Test
    public void withdrawSuccessfulTest() throws InvalidTransferException {

        account.withdraw(BigDecimal.ONE);
        account.withdraw(BigDecimal.ONE);
        account.withdraw(BigDecimal.ONE);
        account.withdraw(BigDecimal.ONE);
        account.withdraw(BigDecimal.ONE);

        assertEquals(account.getBalance(), new BigDecimal(5));
    }

    @Test
    public void withdrawUnSuccessfulTest() {
        try {
            account.withdraw(new BigDecimal(20));

            fail("the method does not throw when I expected it to");
        } catch (InvalidTransferException e) {

        }
    }

    @Test
    public void depositSuccessfulTest() throws InvalidTransferException {
        account.deposit(new BigDecimal(100.0012));

        assertEquals(account.getBalance(), new BigDecimal(110.0012));
    }

    @Test
    public void depositUnSuccessfulTest() {
        try {
            account.deposit(new BigDecimal(-20));
            fail("the method does not throw when I expected it to");
        } catch (IllegalArgumentException e) {

        }
    }


    @Test(timeout = 20000)
    public void multiThreadWithdrawTest() throws InterruptedException {

        // default balance is 10 and we withdraw 9 times (1 euro). Result should be one
        Thread t1 = new Thread(new AccountWithdraw());
        Thread t2 = new Thread(new AccountWithdraw());
        Thread t3 = new Thread(new AccountWithdraw());
        Thread t4 = new Thread(new AccountWithdraw());
        Thread t5 = new Thread(new AccountWithdraw());
        Thread t6 = new Thread(new AccountWithdraw());
        Thread t7 = new Thread(new AccountWithdraw());
        Thread t8 = new Thread(new AccountWithdraw());
        Thread t9 = new Thread(new AccountWithdraw());
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();
        assertEquals(account.getBalance().intValue(), 1);
    }

    @Test(timeout = 20000)
    public void multiThreadDepositTest() throws InterruptedException {
        // default balance is 10 and we add 9 euro. Result should be 19
        Thread t1 = new Thread(new AccountDeposit());
        Thread t2 = new Thread(new AccountDeposit());
        Thread t3 = new Thread(new AccountDeposit());
        Thread t4 = new Thread(new AccountDeposit());
        Thread t5 = new Thread(new AccountDeposit());
        Thread t6 = new Thread(new AccountDeposit());
        Thread t7 = new Thread(new AccountDeposit());
        Thread t8 = new Thread(new AccountDeposit());
        Thread t9 = new Thread(new AccountDeposit());
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();

        assertEquals(account.getBalance().intValue(), 19);
    }

    private class AccountWithdraw implements Runnable {
        public void run() {
            try {
                account.withdraw(BigDecimal.ONE);
            } catch (Exception ie) {
                throw new RuntimeException();
            }
        }
    }

    private class AccountDeposit implements Runnable {
        public void run() {
            try {
                account.deposit(BigDecimal.ONE);
            } catch (Exception ie) {
                throw new RuntimeException();
            }
        }
    }
}
