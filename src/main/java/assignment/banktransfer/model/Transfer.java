package assignment.banktransfer.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Transfer represents the information about a bank transfer.
 */
public class Transfer {

    private UUID transferId;
    private Account from;
    private Account to;
    private BigDecimal amount;
    private TransferStatus status;
    private Date date;
    private String description;

    public Transfer(Account from, Account to, BigDecimal amount, TransferStatus status, String description) {
        this.transferId = UUID.randomUUID();
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.status = status;
        this.date = new Date();
        this.description = description;
    }

    public Transfer(){

    }

    public UUID getTransferId() {
        return transferId;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

}