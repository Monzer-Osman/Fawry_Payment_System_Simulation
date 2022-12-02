package PaymentServices;

public class Transaction {
    private String from;
    private String to;
    private int transactionAmount;
    private TransactionStatus transactionStatus;

    public enum TransactionStatus {
        COMPLETED,
        FAILED,
        REJECTED
        };

    public Transaction(String from, String to, TransactionStatus transactionStatus, int transactionAmount) {
        this.from = from;
        this.to = to;
        this.transactionStatus = transactionStatus;
        this.transactionAmount = transactionAmount;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
