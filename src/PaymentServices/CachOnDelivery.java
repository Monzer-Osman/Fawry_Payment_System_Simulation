package PaymentServices;

public class CachOnDelivery implements PaymentService{
    @Override
    public Transaction pay(String receiverAccountNumber, int amount) {
        return new Transaction("Cash", receiverAccountNumber,
                Transaction.TransactionStatus.COMPLETED, amount);
    }
}
