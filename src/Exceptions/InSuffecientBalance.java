package Exceptions;

public class InSuffecientBalance extends Exception{
    public InSuffecientBalance() {
        super();
    }

    public InSuffecientBalance(String message) {
        super(message);
    }
}
