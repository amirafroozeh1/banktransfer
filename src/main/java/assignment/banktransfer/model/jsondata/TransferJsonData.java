package assignment.banktransfer.model.jsondata;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * This class represents the transfer data received from the body of POST request.
 *
 * Example:
 * {
 *    "from": "abf46d19-91e6-4cac-94ba-a4ed686984dd",
 *    "to": "010abb0f-ddd9-407f-bae6-3ffce124c81b",
 *    "amount": 10,
 *    "description": "sample description"
 * }
 */
public class TransferJsonData {

    private UUID from;
    private UUID to;
    private BigDecimal amount;
    private String description;

    public TransferJsonData(UUID from, UUID to, BigDecimal amount, String description) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
    }

    // Empty constructor is needed for JSON (de)serialization
    public TransferJsonData() {

    }

    public UUID getFrom() {
        return from;
    }

    public UUID getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

}
