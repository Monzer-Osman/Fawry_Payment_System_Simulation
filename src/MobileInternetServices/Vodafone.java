package MobileInternetServices;

public class Vodafone implements MobileRechargeService, InternetPaymentService{
    public final String BANK_ACCOUNT_NUMBER = "01010101010";
    private static Vodafone vodafoneObject;

    private Vodafone(){
    }

    public static Vodafone getInstance(){
        if(vodafoneObject == null) {
            vodafoneObject = new Vodafone();
        }

        return vodafoneObject;
    }

    @Override
    public void rechargeInternetBy(String landLineNumber, int amount) {
        //TODO send to vodafone api the landline and the amount to be recharged
    }

    @Override
    public void rechargeNumberBy(String phoneNumber, int amount) {
        //TODO send to vodafone api the phoneNumber and the amount to be recharged
    }
}
