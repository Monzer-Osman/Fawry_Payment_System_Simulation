import Data.DataBaseHandler;
import Data.DataBaseTest;
import Data.StorageInterface;
import Data.User;
import DonationsServices.CancerHospital;
import DonationsServices.DonationService;
import DonationsServices.NGO;
import DonationsServices.School;
import Exceptions.ServiceNotFoundException;
import Exceptions.WrongChoiceException;
import LandlineServices.LandlineService;
import LandlineServices.MonthlyReceipt;
import LandlineServices.QuarterReceipt;
import MobileInternetServices.*;
import PaymentServices.CreditCard;
import PaymentServices.PaymentService;
import PaymentServices.Transaction;
import java.util.ArrayList;
import java.util.Scanner;
    
public class Main {
    private static Scanner scan;
    private static User user;
    private static Menu programMenu;
    private static StorageInterface database;
    private static DataBaseHandler dataBaseHandler;
    private static LoginSubSystem loginSubSystem;
    private static PaymentService paymentService;

    public static void main(String[] args) {
        programMenu = new Menu();
        database = new DataBaseTest();
        dataBaseHandler = new DataBaseHandler(database);
        loginSubSystem = new LoginSubSystem(dataBaseHandler);
        user = new User("-", "-", "-");
        scan = new Scanner(System.in);
        int loginAttempts = 0;

        signUpFakeUsers(loginSubSystem);
        programMenu.displayWelcomeMessage();

        while (loginAttempts < 4) {
            programMenu.startUpMenu();

            int choice = scan.nextInt();
            if (choice == 1) {
                login();
            } else if (choice == 2) {
                signUp();
            }

            if (!user.getEmail().equals("-")) {
                while (choice != 0) {
                    programMenu.mainMenu();
                    choice = scan.nextInt();
                    switch (choice) {
                        case -1:
                            programMenu.mainMenu();
                            break;
                        case 1:
                            queryAndDisplayServices();
                            break;
                        case 2:
                            //TODO complete the business logic
                            break;
                        case 3:
                            addFundsToWallet();
                            break;
                        case 4:
                            requestRefund();
                            break;
                    }
                }
            }
            loginAttempts += 1;
        }
    }

    private static void login() {
        System.out.println("Enter Your Email ");
        String email = scan.next();

        System.out.println("Enter Your Password ");
        String password = scan.next();

        try {
            user = loginSubSystem.signIn(email, password);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    private static void signUp() {
        System.out.println("Enter Your Email");
        String email = scan.next();

        System.out.println("Enter Your Username");
        String username = scan.next();

        System.out.println("Enter Your Password");
        String password = scan.next();

        try {
            user = loginSubSystem.signUp(email, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    private static void queryAndDisplayServices() {
        try {
            System.out.println("Enter Query: ");
            String query = scan.nextLine();
            query = scan.nextLine();
            queryServices(query);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    public static void queryServices(String query) throws ServiceNotFoundException {
        if (query.contains("mobile recharge services")) {
            chooseFromMobileRechargeServices();
        } else if (query.contains("internet payment services")) {
            chooseFromInternetPaymentServices();
        } else if (query.contains("landline services")) {
            chooseFromLandlineServices();
        } else if (query.contains("donation services")) {
            chooseFromDonationsServices();
        } else {
            throw new ServiceNotFoundException("Service Not-Found");
        }
    }

    private static void chooseFromMobileRechargeServices() {
        displayMobileRechargeServices();
        System.out.println("Enter choice : ");
        int choice = scan.nextInt();
        if (choice == 1) {
            makeMRSTransaction(Vodafone.getInstance());
        } else if (choice == 2) {
            makeMRSTransaction(Etisalat.getInstance());
        } else if (choice == 3) {
            makeMRSTransaction(We.getInstance());
        } else if (choice == 4) {
            makeMRSTransaction(Orange.getInstance());
        }
    }

    private static void displayMobileRechargeServices() {
        System.out.println("1-Vodafone");
        System.out.println("2-Etisalat");
        System.out.println("3-We");
        System.out.println("4-Orange");
    }

    private static void makeMRSTransaction(MobileRechargeService mobileRechargeService) {
        System.out.println("Enter PhoneNumber");
        String phoneNumber = scan.next();
        System.out.println("Enter Recharge Amount");
        int amount = scan.nextInt();
        programMenu.displayPaymentMethods();
        paymentService = choosePaymentMethod();
        if (paymentService != null) {
            Transaction newTransaction = paymentService.pay(
                    Vodafone.getInstance().BANK_ACCOUNT_NUMBER, amount);
            dataBaseHandler.addTransaction(newTransaction);
            if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED)
                mobileRechargeService.rechargeNumberBy(phoneNumber, amount);
        }
    }

    private static void chooseFromInternetPaymentServices() {
        displayInternetPaymentServices();
        System.out.println("Enter choice: ");
        int choice = scan.nextInt();
        if (choice == 1) {
            makeIPSTransaction(
                    Vodafone.getInstance(), Vodafone.getInstance().BANK_ACCOUNT_NUMBER);
        } else if (choice == 2) {
            makeIPSTransaction(
                    Etisalat.getInstance(), Etisalat.getInstance().BANK_ACCOUNT_NUMBER);
        } else if (choice == 3) {
            makeIPSTransaction(
                    We.getInstance(), We.getInstance().BANK_ACCOUNT_NUMBER);
        } else if (choice == 4) {
            makeIPSTransaction(
                    Orange.getInstance(), Orange.getInstance().BANK_ACCOUNT_NUMBER);
        }
    }

    private static void displayInternetPaymentServices() {
        System.out.println("1-Vodafone");
        System.out.println("2-Etisalat");
        System.out.println("3-We");
        System.out.println("4-Orange");
    }

    private static void makeIPSTransaction(
            InternetPaymentService internetPaymentService, String bankAccount) {
        System.out.println("Enter Landline Number");
        String landLineNumber = scan.next();
        System.out.println("Enter Recharge Amount");
        int amount = scan.nextInt();
        programMenu.displayPaymentMethods();
        paymentService = choosePaymentMethod();
        if (paymentService != null) {
            Transaction newTransaction = paymentService.pay(bankAccount, amount);
            dataBaseHandler.addTransaction(newTransaction);
            if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED)
                internetPaymentService.rechargeInternetBy(landLineNumber, amount);
        }
    }

    private static void chooseFromLandlineServices() {
        displayLandlineServices();
        System.out.println("Enter choice : ");
        int choice = scan.nextInt();
        if (choice == 1) {
            makeMLLTransaction(MonthlyReceipt.getInstance(),
                    MonthlyReceipt.getInstance().BANK_ACCOUNT);
        } else if (choice == 2) {
            makeQLLTransaction(QuarterReceipt.getInstance(),
                    QuarterReceipt.getInstance().BANK_ACCOUNT);
        }
    }

    private static void displayLandlineServices() {
        System.out.println("1-Monthly Receipt");
        System.out.println("2-Quarter Receipt");
    }

    private static void makeMLLTransaction(LandlineService landlineService, String bankAccount) {
        System.out.println("Enter Receipt Number");
        String receiptNumber = scan.next();
        System.out.println("Enter Receipt Fees");
        int amount = scan.nextInt();
        programMenu.displayPaymentMethods();
        paymentService = choosePaymentMethod();
        if (paymentService != null) {
            Transaction newTransaction = paymentService.pay(bankAccount, amount);
            dataBaseHandler.addTransaction(newTransaction);
            if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED)
                landlineService.payReceipt(receiptNumber);
        }
    }

    private static void makeQLLTransaction(LandlineService landlineService, String bankAccount) {
        System.out.println("Enter Receipt Number");
        String receiptNumber = scan.next();
        System.out.println("Enter Receipt Fees");
        int amount = scan.nextInt();
        programMenu.displayPaymentMethods();
        paymentService = choosePaymentMethod();
        if (paymentService != null) {
            Transaction newTransaction = paymentService.pay(bankAccount, amount);
            dataBaseHandler.addTransaction(newTransaction);
            if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED)
                landlineService.payReceipt(receiptNumber);
        }
    }

    private static void chooseFromDonationsServices() {
        displayDonationsServices();
        int choice = scan.nextInt();
        if (choice == 1) {
            CancerHospital cancerHospital = new CancerHospital();
            makeDonationTransaction(cancerHospital, cancerHospital.BANK_ACCOUNT);
        } else if (choice == 2) {
            School faisalSchool = new School();
            makeDonationTransaction(faisalSchool, faisalSchool.BANK_ACCOUNT);
        } else if (choice == 3) {
            NGO resalaOrganization = new NGO();
            makeDonationTransaction(resalaOrganization, resalaOrganization.BANK_ACCOUNT);
        }
    }

    private static void displayDonationsServices() {
        System.out.println("1-Cancer Hospital");
        System.out.println("2-AlFaisal School");
        System.out.println("3-Resala NGO");
    }

    private static void makeDonationTransaction(DonationService donationService, String bankAccount) {
        System.out.println("Enter Donation Amount");
        int donationAmount = scan.nextInt();
        programMenu.displayPaymentMethods();
        paymentService = choosePaymentMethod();
        if (paymentService != null) {
            Transaction newTransaction = paymentService.pay(bankAccount, donationAmount);
            dataBaseHandler.addTransaction(newTransaction);
            if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED)
                donationService.donateBy(donationAmount);
        }
    }

    private static PaymentService choosePaymentMethod() {
        try {
            System.out.println("Enter choice : ");
            int paymentMethodChoice = scan.nextInt();
            if (paymentMethodChoice == 1) {
                System.out.println("Enter FirstName");
                String firstName = scan.next();
                System.out.println("Enter LastName");
                String lastName = scan.next();
                System.out.println("Enter Bank AccountNumber");
                String bankAccountNumber = scan.next();
                return new CreditCard(firstName, lastName, bankAccountNumber);
            } else if (paymentMethodChoice == 2) {
                return user.getWallet();
            } else {
                throw new WrongChoiceException("Wrong-Choice :(");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            return null;
        }
    }

    private static void addFundsToWallet() {
        System.out.println("Enter FirstName : ");
        String firstName = scan.next();
        System.out.println("Enter LastName : ");
        String lastName = scan.next();
        System.out.println("Enter CreditCard Number : ");
        String cardNumber = scan.next();
        System.out.println("Enter Amount : ");
        int amount = scan.nextInt();
        CreditCard creditCard = new CreditCard(
                firstName, lastName, cardNumber);
        user.addFundsToWallet(creditCard, amount);
        System.out.println("Now you have " + user.getBalance() + " in your wallet");
    }

    private static void requestRefund() {
        ArrayList<Transaction> transactions = dataBaseHandler.getTransactions();
        if (!transactions.isEmpty()) {
            displayTransactions(transactions);
            int transactionNumber = scan.nextInt() - 1;
            dataBaseHandler.addRefundRequest(transactions.get(transactionNumber));
            System.out.println("Refund Request has made successfully");
        }
    }

    public static void displayTransactions(ArrayList<Transaction> transactions) {
        for (int i = 1; i <= transactions.size(); i++) {
            System.out.println(i + "-From Account Number "
                    + transactions.get(i - 1).getFrom() + " - to - " +
                    transactions.get(i - 1).getTo() + " Status " +
                    "(" + transactions.get(i - 1).getTransactionStatus() + ")");
        }
    }

    private static void signUpFakeUsers(LoginSubSystem loginSubSystem) {
        try {
            User user3 = loginSubSystem.signUp("heloow@hello.com", "hi", "Zer2984");
            CreditCard userCard = new CreditCard("Osama", "Ahmed", "28392382323");
            user3.addFundsToWallet(userCard, 50);

            User user5 = loginSubSystem.signUp("Eyad@hotmail.com", "Khalid021", "helloWorld");
            CreditCard userCard2 = new CreditCard("Mohammad@yahoo.com", "Hamed909", "2839238323");
            user5.addFundsToWallet(userCard2, 100);

            loginSubSystem.signUp("Eyad2@gmail.com", "Khalid", "helloworld");
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }
}