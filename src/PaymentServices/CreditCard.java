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
    public Transaction pay(String receiverAccountNumber, int amount) {
        return new Transaction(userAccountNumber, receiverAccountNumber,
                Transaction.TransactionStatus.COMPLETED, amount);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserAccountNumber() {
        return userAccountNumber;
    }

    public void setUserAccountNumber(String userAccountNumber) {
        this.userAccountNumber = userAccountNumber;
    }
}
