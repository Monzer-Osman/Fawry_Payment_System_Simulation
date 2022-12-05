package LandlineServices;

public class QuarterReceipt implements LandlineService{
    public final String BANK_ACCOUNT = "827393292311";
    private static QuarterReceipt quarterReceiptObject;

    private QuarterReceipt(){
    }
    @Override
    public void payReceipt(String receiptNumber) {
        //TODO call the service provider api and pass the receiptNumber
    }

    public static QuarterReceipt getInstance() {
        if(quarterReceiptObject == null)
            quarterReceiptObject = new QuarterReceipt();
        return quarterReceiptObject;
    }
}
