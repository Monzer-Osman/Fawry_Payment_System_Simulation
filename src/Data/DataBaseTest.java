package Data;

import Discount.Discount;
import Exceptions.EmailNotFoundException;
import Exceptions.EmailRegisteredBeforeException;
import Exceptions.UserNameRegisteredBeforeException;
import Exceptions.WrongPasswordException;
import PaymentServices.Transaction;

import java.util.ArrayList;

public class DataBaseTest implements IStorage {
    ArrayList<User> users;
    ArrayList<Transaction> transactions;
    ArrayList<Transaction> refundRequests;
    ArrayList<Discount> discounts;

    public DataBaseTest() {
        users = new ArrayList<>();
        transactions = new ArrayList<>();
        discounts = new ArrayList<>();
    }
    @Override
    public User signIn(String email, String password) throws Exception{
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }

        if (users.isEmpty() || !checkIfEmailInUsersArray(email)) {
            throw new EmailNotFoundException("This Email Not-Exist Try Sign-Up");
        } else {
            throw new WrongPasswordException("Wrong password");
        }
    }

    @Override
    public User signUp(String email, String username, String pasword) throws Exception{
        if (checkIfEmailInUsersArray(email)) {
            throw new EmailRegisteredBeforeException("Email is already registered");
        } else if (checkIfUsernameInUsersArray(username)) {
            throw new UserNameRegisteredBeforeException("Username is already registered");
        } else {
            User newUser = new User(email, username, pasword);
            users.add(newUser);
            return newUser;
        }
    }

    @Override
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public void addRefundRequest(Transaction transaction) {
        refundRequests.add(transaction);
    }

    private boolean checkIfEmailInUsersArray(String email){
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfUsernameInUsersArray(String username){
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }
}
