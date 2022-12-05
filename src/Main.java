import Data.*;
import Discount.Discount;
import Discount.DiscountHandler;
import DonationsServices.*;
import Exceptions.*;
import LandlineServices.*;
import MobileInternetServices.*;
import PaymentServices.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scan;
    private static User user;
    private static Menu programMenu;
    private static IStorage database;
    private static DataBaseHandler dataBaseHandler;
    private static LoginSubSystem loginSubSystem;
    private static PaymentService paymentService;
    private static DiscountHandler discountHandler;

    public static void main(String[] args) {
        programMenu = new Menu();
        database = new DataBaseTest();
        dataBaseHandler = new DataBaseHandler(database);
        loginSubSystem = new LoginSubSystem(dataBaseHandler);
        discountHandler = new DiscountHandler(dataBaseHandler);
        user = new User("-", "-", "-");
        scan = new Scanner(System.in);
        int loginAttempts = 0;

        signUpFakeUsers();
        addDiscount();
        displayWelcomeMessage();
        while (loginAttempts < 4) {
            displayStartUpMenu();

            int choice = scan.nextInt();
            if (choice == 1) {
                login();
            } else if (choice == 2) {
                signUp();
            }

            if (!user.getEmail().equals("-")) {
                while (choice != 0) {
                    displayMainMenu();
                    choice = scan.nextInt();
                    switch (choice) {
                        case -1:
                            displayMainMenu();
                            break;
                        case 1:
                            queryAndDisplayServices();
                            break;
                        case 2:
                            displayAvailableDiscounts();
                            break;
                        case 3:
                            addFundsToWallet();
                            break;
                        case 4:
                            requestRefund();
                            break;
                    }
                }
                return;
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
        if (query.contains("mobile")) {
            chooseFromMobileRechargeServices();
        } else if (query.contains("internet ")) {
            chooseFromInternetPaymentServices();
        } else if (query.contains("landline")) {
            chooseFromLandlineServices();
        } else if (query.contains("donation")) {
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
            makeMRSTransaction(Vodafone.getInstance(),
                    Vodafone.getInstance().BANK_ACCOUNT_NUMBER);
        } else if (choice == 2) {
            makeMRSTransaction(Etisalat.getInstance(),
                    Etisalat.getInstance().BANK_ACCOUNT_NUMBER);
        } else if (choice == 3) {
            makeMRSTransaction(We.getInstance(),
                    We.getInstance().BANK_ACCOUNT_NUMBER);
        } else if (choice == 4) {
            makeMRSTransaction(Orange.getInstance(),
                    Orange.getInstance().BANK_ACCOUNT_NUMBER);
        }
    }

    private static void displayMobileRechargeServices() {
        System.out.println("1-Vodafone");
        System.out.println("2-Etisalat");
        System.out.println("3-We");
        System.out.println("4-Orange");
    }

    private static void makeMRSTransaction(
            MobileRechargeService mobileRechargeService, String bankAccountNumber) {
        System.out.println("Enter PhoneNumber");
        String phoneNumber = scan.next();
        System.out.println("Enter Recharge Amount");
        int amount = scan.nextInt();
        displayPaymentMethods();
        paymentService = choosePaymentMethod();
        System.out.println("Have a DiscountCode ?\nEnter it / Enter -1 to continue ");
        String discountCode = scan.next();
        if (paymentService != null) {
            try {
                if (!discountCode.equals("-1")) {
                    amount = (int) ((float)amount * discountHandler.getDiscountAmount(
                            discountCode, Discount.DiscountType.MobileRechargeServices));
                }
                Transaction newTransaction = paymentService.pay(bankAccountNumber, amount);
                dataBaseHandler.addTransaction(newTransaction);
                if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED) {
                    mobileRechargeService.rechargeNumberBy(phoneNumber, amount);
                }
            } catch(Exception e){
                System.out.println(e.getMessage().toString());
            }
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
        displayPaymentMethods();
        paymentService = choosePaymentMethod();
        System.out.println("Have a DiscountCode ?\nEnter it / Enter -1 to continue ");
        String discountCode = scan.next();
        if (paymentService != null) {
            try {
                if (!discountCode.equals("-1")) {
                    amount = (int) ((float)amount * discountHandler.getDiscountAmount(
                            discountCode, Discount.DiscountType.InternetPaymentServices));
                }
                Transaction newTransaction = paymentService.pay(bankAccount, amount);
                dataBaseHandler.addTransaction(newTransaction);
                if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED) {
                    internetPaymentService.rechargeInternetBy(landLineNumber, amount);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage().toString());
            }
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
        displayPaymentMethods();
        paymentService = choosePaymentMethod();
        System.out.println("Have a DiscountCode ?\nEnter it / Enter -1 to continue ");
        String discountCode = scan.next();
        if (paymentService != null) {
            try {
                if (!discountCode.equals("-1")) {
                    amount = (int) (amount * discountHandler.getDiscountAmount(
                            discountCode, Discount.DiscountType.LandlineServices));
                }
                Transaction newTransaction = paymentService.pay(bankAccount, amount);
                dataBaseHandler.addTransaction(newTransaction);
                if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED) {
                    landlineService.payReceipt(receiptNumber);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage().toString());
            }
        }
    }

    private static void makeQLLTransaction(LandlineService landlineService, String bankAccount) {
        System.out.println("Enter Receipt Number");
        String receiptNumber = scan.next();
        System.out.println("Enter Receipt Fees");
        int amount = scan.nextInt();
        displayPaymentMethods();
        paymentService = choosePaymentMethod();
        System.out.println("Have a DiscountCode ?\nEnter it / Enter -1 to continue ");
        String discountCode = scan.next();
        if (paymentService != null) {
            try {
                if (!discountCode.equals("-1")) {
                    amount = (int) (amount * discountHandler.getDiscountAmount(
                            discountCode, Discount.DiscountType.LandlineServices));
                }
                Transaction newTransaction = paymentService.pay(bankAccount, amount);
                dataBaseHandler.addTransaction(newTransaction);
                if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED) {
                    landlineService.payReceipt(receiptNumber);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage().toString());
            }
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
        displayPaymentMethods();
        paymentService = choosePaymentMethod();
        System.out.println("Have a DiscountCode ?\nEnter it / Enter -1 to continue ");
        String discountCode = scan.next();
        if (paymentService != null) {
            try {
                if (!discountCode.equals("-1")) {
                    donationAmount = (int) (donationAmount * discountHandler.getDiscountAmount(
                            discountCode, Discount.DiscountType.DonationServices));
                }
                Transaction newTransaction = paymentService.pay(bankAccount, donationAmount);
                dataBaseHandler.addTransaction(newTransaction);
                if (newTransaction.getTransactionStatus() == Transaction.TransactionStatus.COMPLETED) {
                    donationService.donateBy(donationAmount);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage().toString());
            }
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
        try {
            user.addFundsToWallet(creditCard, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
        System.out.println("Now you have " + user.getBalance() + " pound in your wallet");
    }

    private static void displayAvailableDiscounts() {
        discountHandler.displayAvailableDiscounts();
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
            System.out.println(i + "-From Bank Account Number "
                    + transactions.get(i - 1).getFrom() + " - to - Bank Account Number" +
                    transactions.get(i - 1).getTo() + " Status " +
                    "(" + transactions.get(i - 1).getTransactionStatus() + ")");
        }
    }

    private static void signUpFakeUsers() {
        try {
            User user3 = loginSubSystem.signUp("ahmed@hello.com", "osman", "Zer2984");
            CreditCard userCard = new CreditCard("ahmed", "osman", "28392382323");
            user3.addFundsToWallet(userCard, 50);

            User user5 = loginSubSystem.signUp("Eyad@hotmail.com", "khalid", "helloWorld");
            CreditCard userCard2 = new CreditCard("Eyad", "khalid", "2839238323");
            user5.addFundsToWallet(userCard2, 100);

            loginSubSystem.signUp("osama@gmail.com", "Mohammad", "osama123");
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    private static void addDiscount() {
        try {
            Discount discount = new Discount(Discount.DiscountType.LandlineServices,
                    "202", 20);
            discountHandler.addDiscount(discount);

            discount = new Discount(Discount.DiscountType.MobileRechargeServices,
                    "207", 50);
            discountHandler.addDiscount(discount);

            discount = new Discount(Discount.DiscountType.InternetPaymentServices,
                    "A2HL012", 30);
            discountHandler.addDiscount(discount);
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    private static void displayWelcomeMessage() {
        programMenu.displayWelcomeMessage();
    }

    private static void displayStartUpMenu() {
        programMenu.startUpMenu();
    }

    private static void displayPaymentMethods() {
        programMenu.displayPaymentMethods();
    }

    private static void displayMainMenu() {
        programMenu.mainMenu();
    }
}

