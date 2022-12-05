package PaymentServices;

public class CreditCard implements PaymentService{
    private String firstName;
    private String lastName;
    private String userAccountNumber;

    public CreditCard(String firstName, String lastName, String userAccountNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userAccountNumber = userAccountNumber;
    }

    @Override
    public Transaction pay(String receiverAccountNumber, int amount) throws Exception{
        return new Transaction(userAccountNumber, receiverAccountNumber,
                Transaction.TransactionStatus.COMPLETED, amount);
    }

}
