package assignment.banktransfer.service;

import assignment.banktransfer.model.Transfer;
import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.jsondata.TransferJsonData;
import assignment.banktransfer.repository.TransferRepository;
import assignment.banktransfer.model.TransferStatus;
import assignment.banktransfer.util.exception.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The TransferServiceImpl provides an implementation of TransferService, by adding a new transfer, finding
 * a account by id, finding by name, delete all and checking exist of a transfer.
 *
 * @author Amir Afroozeh
 * @version 1.0
 */
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public Transfer findTransferById(UUID transferId) {
        return transferRepository.findTransferById(transferId);
    }

    @Override
    public void addTransfer(Transfer transfer) {
        transferRepository.addTransfer(transfer);
    }

    @Override
    public List<Transfer> findTransferByAccount(Account from) {
        return transferRepository.findTransferByAccount(from);
    }

    @Override
    public boolean isValid(TransferJsonData transferJsonData) {
        if (transferJsonData.getFrom() == null || transferJsonData.getTo() == null) {
            return false;
        }
        Account from = accountService.findById(transferJsonData.getFrom());
        Account to = accountService.findById(transferJsonData.getTo());

        if (from == null || to == null) {
            return false;
        }

        return Validation.isBalancePositive(transferJsonData.getAmount());
    }

    @Override
    public Transfer executeTransfer(Account from, Account to, BigDecimal amount, String description) {
        try {
            from.withdraw(amount);
            to.deposit(amount);
        } catch (Exception e) {
            return new Transfer(from, to, amount, TransferStatus.FAILURE, description);
        }
        return new Transfer(from, to, amount, TransferStatus.SUCCESS, description);
    }

    @Override
    public void deleteAll() {
        transferRepository.deleteAll();
    }

}
