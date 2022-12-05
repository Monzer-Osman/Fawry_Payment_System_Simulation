package MobileInternetServices;

public class We implements MobileRechargeService, InternetPaymentService{
    public final String BANK_ACCOUNT_NUMBER = "01010101010";
    private static We weObject;

    private We(){
    }

    public static We getInstance(){
        if(weObject == null) {
            weObject = new We();
        }

        return weObject;
    }

    @Override
    public void rechargeInternetBy(String landLineNumber, int amount) {
        //TODO send to We api the phoneNumber and the amount to be recharged
    }

    @Override
    public void rechargeNumberBy(String phoneNumber, int amount) {
        //TODO send to We api the phoneNumber and the amount to be recharged
    }
}
