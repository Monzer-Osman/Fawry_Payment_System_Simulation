package LandlineServices;

public class MonthlyReceipt implements LandlineService{
    public final String BANK_ACCOUNT = "827393292311";
    private static MonthlyReceipt monthlyReceiptObject;

    private MonthlyReceipt(){
    }
    @Override
    public void payReceipt(String receiptNumber) {
        //TODO call the service provider api and pass the receiptNumber
    }

    public static MonthlyReceipt getInstance() {
        if(monthlyReceiptObject == null)
            monthlyReceiptObject = new MonthlyReceipt();
        return monthlyReceiptObject;
    }
}

