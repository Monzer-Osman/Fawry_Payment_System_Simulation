package Exceptions;

public class UserNameRegisteredBeforeException extends Exception{
    public UserNameRegisteredBeforeException() {
        super();
    }

    public UserNameRegisteredBeforeException(String message) {
        super(message);
    }
}
