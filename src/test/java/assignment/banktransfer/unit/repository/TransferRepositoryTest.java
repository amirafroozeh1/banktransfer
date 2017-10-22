package assignment.banktransfer.unit.repository;

import assignment.banktransfer.model.Transfer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TransferRepositoryTest {

    private Map<UUID, Transfer> transfers;
    private UUID transferId;
    @Mock
    private Transfer transfer;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        transferId = UUID.randomUUID();
        transfers = new HashMap<>();
    }

    @Test
    public void transferTest() throws Exception {
        Map<UUID, Transfer> spy = spy(transfers);

        when(spy.get(transferId)).thenReturn(transfer);

        assertEquals(transfer, spy.get(transferId));
    }
}