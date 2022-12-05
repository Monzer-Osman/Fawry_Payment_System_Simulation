package Exceptions;

public class InvalidDiscountType extends Exception{
    public InvalidDiscountType() {
        super();
    }

    public InvalidDiscountType(String message) {
        super(message);
    }
}
