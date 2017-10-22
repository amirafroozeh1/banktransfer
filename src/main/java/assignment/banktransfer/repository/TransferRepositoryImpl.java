package assignment.banktransfer.repository;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * The TransferRepositoryImpl provides an implementation of TransferRepository, by adding a new transfer, finding
 * a transfer by id, finding by name, and delete all transfers.
 *
 * @author  Amir Afroozeh
 * @version 1.0
 */
@Component
public class TransferRepositoryImpl implements TransferRepository {

    private Map<UUID, Transfer> transfers = new HashMap<>();

    @Override
    public Transfer findTransferById(UUID transferId) {
        return transfers.get(transferId);
    }

    @Override
    public void addTransfer(Transfer transfer) {
        transfers.put(transfer.getTransferId(), transfer);
    }

    @Override
    public List<Transfer> findTransferByAccount(Account from) {
        List<Transfer> transferList = new ArrayList<>();
        for (Map.Entry<UUID, Transfer> entry : transfers.entrySet()) {
            if (entry.getValue().getFrom() == from)
                transferList.add(findTransferById(entry.getKey()));
        }
        return transferList;
    }

    @Override
    public void deleteAll() {
        transfers.clear();
    }
}
