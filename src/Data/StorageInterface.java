package Data;

import PaymentServices.Transaction;

import java.util.ArrayList;

public interface StorageInterface {
    public User signIn(String email, String password) throws Exception;
    public User signUp(String email, String username, String password) throws Exception;
    public ArrayList<Transaction> getTransactions();
    public void addTransaction(Transaction transaction);
    public void addRefundRequest(Transaction transaction);
}
