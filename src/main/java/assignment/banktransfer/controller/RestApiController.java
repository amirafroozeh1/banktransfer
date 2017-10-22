package assignment.banktransfer.controller;

import assignment.banktransfer.model.Account;
import assignment.banktransfer.model.Transfer;
import assignment.banktransfer.model.jsondata.AccountJsonData;
import assignment.banktransfer.model.jsondata.TransferJsonData;
import assignment.banktransfer.service.AccountService;
import assignment.banktransfer.service.TransferService;
import assignment.banktransfer.model.TransferStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

/**
 * The RestApiController class provides the REST API for the Bank Transfer Service application.
 *
 * @author Amir Afroozeh
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class RestApiController {

    private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    /**
     * This method finds and account by Id.
     *
     * @param id the id of the account as an {@link UUID}
     * @return the {@link Account} instance with the given id if found.
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findAccountById(@PathVariable("id") UUID id) {
        logger.info("Fetching account with id {}", id);
        Account account = accountService.findById(id);
        if (account == null) {
            logger.error("Account with id {} not found.", id);
            return new ResponseEntity<>("Account with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        logger.error("Account with id {} found.", id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * This method creates a new account and returns the unique account if of the account.
     *
     * @return the id of the account (UUID)
     */
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(@RequestBody AccountJsonData accountJsonData) {

        logger.info("Creating a new account with name {}", accountJsonData.getName());

        if (!accountService.isValid(accountJsonData)) {
            logger.error("Unable to create a new account");
            return new ResponseEntity<>("Unable to create a new account", HttpStatus.BAD_REQUEST);
        }
        Account account = new Account(accountJsonData.getName(), accountJsonData.getBalance());
        accountService.addAccount(account);
        return new ResponseEntity<>(account.getAccountId(), HttpStatus.OK);
    }

    /**
     * This method searches for an account by name and returns the set of found accounts.
     *
     * @param name the name of the account to search for
     * @return the set of accounts with the given name
     */
    @RequestMapping(value = "/account/search/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> findAccountByName(@PathVariable("name") String name) {

        logger.info("Fetching account with name {}", name);
        Set<Account> accounts = accountService.findByName(name);
        if (accounts.isEmpty()) {
            logger.error("Account with name {} not found.", name);
            return new ResponseEntity("Account with name " + name + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * This method transfers money from one account to another.
     *
     * @param transferJsonData
     * @return the id of the transfer (UUID)
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseEntity<?> transfer(@RequestBody TransferJsonData transferJsonData) {

        if (!transferService.isValid(transferJsonData)) {
            logger.error("Transfer data not valid");
            return new ResponseEntity<>("transfer data not valid", HttpStatus.BAD_REQUEST);
        }

        logger.info("Execute a new transfer {}");
        Account from = accountService.findById(transferJsonData.getFrom());
        Account to = accountService.findById(transferJsonData.getTo());

        Transfer transfer = transferService.executeTransfer(from, to, transferJsonData.getAmount(), transferJsonData.getDescription());
        if (transfer.getStatus() == TransferStatus.FAILURE) {
            logger.error("Unable to transfer");
            return new ResponseEntity<>("Unable to transfer", HttpStatus.BAD_REQUEST);
        }
        transferService.addTransfer(transfer);
        return new ResponseEntity<>(transfer.getTransferId(), HttpStatus.OK);
    }

    /**
     * This method finds a transfer with the given id and returns the transfer information.
     *
     * @param id the id of the transfer (UUID)
     * @return the found transfer object
     */
    @RequestMapping(value = "/transfer/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findTransferById(@PathVariable("id") UUID id) {

        logger.info("Fetching transfer with id {}", id);
        Transfer transfer = transferService.findTransferById(id);
        if (transfer == null) {
            logger.error("Transfer with id {} not found.", id);
            return new ResponseEntity<>("Transfer with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transfer, HttpStatus.OK);
    }
}
