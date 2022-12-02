package Data;
import PaymentServices.Transaction;

import java.util.ArrayList;

public class DataBaseHandler {
    StorageInterface database;
    public DataBaseHandler(StorageInterface database) {
        this.database = database;
    }
    public User signIn(String email, String password) throws Exception{
        return database.signIn(email, password);
    }
    public User signUp(String email, String username, String password) throws Exception{
        return database.signUp(email, username, password);
    }

    public ArrayList<Transaction> getTransactions() {
        return database.getTransactions();
    }

    public void addTransaction(Transaction transaction) {
        database.addTransaction(transaction);
    }

    public void addRefundRequest(Transaction transaction){
        database.addRefundRequest(transaction);
    }
}
