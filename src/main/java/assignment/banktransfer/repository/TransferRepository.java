package assignment.banktransfer.repository;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.Transfer;

import java.util.List;
import java.util.UUID;

public interface TransferRepository {

    Transfer findTransferById(UUID transferId);

    void addTransfer(Transfer transfer);

    List<Transfer> findTransferByAccount(Account from);

    void deleteAll();
}
