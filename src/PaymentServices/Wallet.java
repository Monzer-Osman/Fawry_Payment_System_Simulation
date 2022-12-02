package PaymentServices;

public class Wallet implements PaymentService {
    private int balance;
    public final String BANK_ACCOUNT = "294902942341";
    public Wallet(){
        balance = 0;
    }
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addBalance(CreditCard userCreditCard, int amount){
        if(userCreditCard.pay(BANK_ACCOUNT, amount).getTransactionStatus()
                == Transaction.TransactionStatus.COMPLETED) {
            balance += amount;
        }
    }
    @Override
    public Transaction pay(String accountNumber, int amount) {
        Transaction transaction = new Transaction(BANK_ACCOUNT, accountNumber,
                Transaction.TransactionStatus.FAILED, amount);

        if(amount <= balance) {
            CreditCard creditCard = new CreditCard("FawryAccount",
                    "FawryAccount", BANK_ACCOUNT);
            if(creditCard.pay(accountNumber, amount).getTransactionStatus()
                    == Transaction.TransactionStatus.COMPLETED){
                balance -= amount;
                transaction.setTransactionStatus(Transaction.TransactionStatus.COMPLETED);
                return transaction;
            }
        }
        return transaction;
    }

}
