package MobileInternetServices;

public class Orange implements MobileRechargeService, InternetPaymentService{
    public final String BANK_ACCOUNT_NUMBER = "0111011111";
    private static Orange orangeObject;
    private Orange(){
    }

    public static Orange getInstance(){
        if(orangeObject == null) {
            orangeObject = new Orange();
        }

        return orangeObject;
    }

    public String getOrangeBankNumber(){
        return BANK_ACCOUNT_NUMBER;
    }

    @Override
    public void rechargeNumberBy(String phoneNumber, int amount) {
        //TODO send to Orange api the phoneNumber and the amount to be recharged
    }

    @Override
    public void rechargeInternetBy(String landLineNumber, int amount) {
        //TODO send to Orange api the phoneNumber and the amount to be recharged
    }
}
