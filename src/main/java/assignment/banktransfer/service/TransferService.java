package assignment.banktransfer.service;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.Transfer;
import assignment.banktransfer.model.jsondata.TransferJsonData;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransferService {

    Transfer findTransferById(UUID transferId);

    void addTransfer(Transfer transfer);

    List<Transfer> findTransferByAccount(Account from);

    boolean isValid(TransferJsonData transferJsonData);

    Transfer executeTransfer(Account from, Account to, BigDecimal amount, String description);

    void deleteAll();

}
