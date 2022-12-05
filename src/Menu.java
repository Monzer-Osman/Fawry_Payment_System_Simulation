public class Menu {
    public void displayWelcomeMessage(){
        System.out.println("*************************");
        System.out.println("Welcome To Fawry System");
        System.out.println("*************************");
    }
    public void startUpMenu(){
        System.out.println("1-Sign In");
        System.out.println("2-Sign Up");
    }

    public void mainMenu(){
        System.out.println("--------------------------");
        System.out.println("0-Exit Program");
        System.out.println("1-Query Services");
        System.out.println("2-Display Available Discounts");
        System.out.println("3-Add Balance To Wallet");
        System.out.println("4-Request Refund");
    }

    public void displayPaymentMethods(){
        System.out.println("How would you like to pay for this Service ? ");
        System.out.println("1-From CreditCard ('default')");
        System.out.println("2-From Wallet");
    }
}
