package MobileInternetServices;

import Discount.Discount;
import Discount.DiscountHandler;
import Exceptions.InvalidDiscountType;

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

    @Override
    public void rechargeInternetBy(String landLineNumber, int amount) {
        //TODO send to Etisalat api the landline number and the amount to be recharged
    }

    @Override
    public void rechargeNumberBy(String phoneNumber, int amount) {
        //TODO send to Etisalat api the phoneNumber and the amount to be recharged
    }
}
