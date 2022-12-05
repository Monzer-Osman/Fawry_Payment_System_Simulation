package Data;

import PaymentServices.CreditCard;
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

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void addFundsToWallet(CreditCard userCreditCard, int amount) throws Exception{
        wallet.addBalance(userCreditCard, amount);
    }
    public int getBalance(){
        return wallet.getBalance();
    }

}
