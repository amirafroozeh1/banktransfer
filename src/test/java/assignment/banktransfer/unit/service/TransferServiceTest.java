package assignment.banktransfer.unit.service;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.Transfer;
import assignment.banktransfer.repository.TransferRepository;
import assignment.banktransfer.service.TransferServiceImpl;
import assignment.banktransfer.service.AccountService;
import assignment.banktransfer.service.TransferService;
import assignment.banktransfer.model.TransferStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

    @InjectMocks
    private TransferService transferService = new TransferServiceImpl();
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private Transfer transfer;
    @Mock
    private List<Transfer> transfers;

    private UUID transferId;
    private Account from;
    private Account to;
    private BigDecimal amount;
    private TransferStatus status;
    private Date date;
    private String description;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        transferId = UUID.randomUUID();
        from = new Account();
        to = new Account();
        amount = new BigDecimal("1000.5");
        status = TransferStatus.SUCCESS;
        date = new Date();
        description = "this is a test";
    }

    @Test
    public void getTransferTest() throws Exception {
        when(transferRepository.findTransferById(transferId)).thenReturn(transfer);

        Transfer retrievedTransfer = transferService.findTransferById(transferId);

        assertThat(retrievedTransfer, is(equalTo(transfer)));
    }

    @Test
    public void addAccountTest() throws Exception {

        Transfer transfer = new Transfer(from, to, amount, status, description);
        doNothing().when(transferRepository).addTransfer(transfer);

        transferService.addTransfer(transfer);

        verify(transferRepository).addTransfer(transfer);
    }

    @Test
    public void findTransferByAccountTest() throws Exception {
        when(transferRepository.findTransferByAccount(from)).thenReturn(transfers);

        List<Transfer> retrievedTransfers = transferService.findTransferByAccount(from);

        assertThat(retrievedTransfers, is(equalTo(transfers)));
    }

}