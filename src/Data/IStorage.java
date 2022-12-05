package Data;

import Discount.Discount;
import PaymentServices.Transaction;

import java.util.ArrayList;

public interface IStorage {
    public User signIn(String email, String password) throws Exception;
    public User signUp(String email, String username, String password) throws Exception;
    public ArrayList<Transaction> getTransactions();
    public void addTransaction(Transaction transaction);
    public void addRefundRequest(Transaction transaction);

    public void addDiscount(Discount discount);

    public ArrayList<Discount> getDiscounts();
}
