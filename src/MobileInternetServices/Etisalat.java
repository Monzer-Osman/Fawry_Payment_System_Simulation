package MobileInternetServices;

public class Etisalat implements MobileRechargeService, InternetPaymentService{
    public final String BANK_ACCOUNT_NUMBER = "01101101101";
    private static Etisalat etisalatObject;
    private Etisalat(){
    }

    public static Etisalat getInstance(){
        if(etisalatObject == null) {
            etisalatObject = new Etisalat();
        }
        return etisalatObject;
    }

    public String getBankNumber(){
        return BANK_ACCOUNT_NUMBER;
    }

    @Override
    public void rechargeNumberBy(String phoneNumber, int amount) {
        //TODO send to Etisalat api the phoneNumber and the amount to be recharged
    }

    @Override
    public void rechargeInternetBy(String landLineNumber, int amount) {
        //TODO send to Orange api the phoneNumber and the amount to be recharged
    }
}
