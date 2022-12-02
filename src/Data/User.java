package Data;

import PaymentServices.CreditCard;
import PaymentServices.PaymentService;
import PaymentServices.Transaction;
import PaymentServices.Wallet;

public class User {
    private String username;
    private String email;
    private String password;
    private Wallet wallet;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void addFundsToWallet(CreditCard userCreditCard, int amount){
        wallet.addBalance(userCreditCard, amount);
    }
    public int getBalance(){
        return wallet.getBalance();
    }

    public Transaction payForService(PaymentService paymentMethod,
                                     String receiverAccountNumber,
                                     int amount) {
            return paymentMethod.pay(receiverAccountNumber, amount);
    }

}
