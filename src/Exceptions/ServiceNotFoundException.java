package Exceptions;

public class ServiceNotFoundException extends Exception{
    public ServiceNotFoundException() {
        super();
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }

}
