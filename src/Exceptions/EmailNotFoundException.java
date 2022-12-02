package Exceptions;

public class EmailNotFoundException extends Exception{
    public EmailNotFoundException() {
        super();
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
