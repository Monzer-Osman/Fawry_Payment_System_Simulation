package PaymentServices;
import Exceptions.InSuffecientBalance;

public class Wallet implements PaymentService {
    public final String BANK_ACCOUNT = "294902942341";
    private int balance;

    public Wallet() {
        balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(CreditCard userCreditCard, int amount) throws Exception {
        if (userCreditCard.pay(BANK_ACCOUNT, amount).getTransactionStatus()
                == Transaction.TransactionStatus.COMPLETED) {
            balance += amount;
        }
    }

    @Override
    public Transaction pay(String accountNumber, int amount) throws Exception {
        if (amount <= balance) {
            CreditCard creditCard = new CreditCard("FawryAccount",
                    "FawryAccount", BANK_ACCOUNT);
            Transaction transaction = creditCard.pay(accountNumber, amount);
            if (transaction.getTransactionStatus().equals(Transaction.TransactionStatus.COMPLETED)) {
                balance -= amount;
            }
            return transaction;
        } else {
            throw new InSuffecientBalance("Insuffecint Balance in Wallet :(");
        }
    }
}
