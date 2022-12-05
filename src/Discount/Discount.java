package Discount;

public class Discount {
    private String discountCode;
    private int discountAmount;
    public enum DiscountType{
        OverAll,
        LandlineServices,
        DonationServices,
        MobileRechargeServices,
        InternetPaymentServices
    };
    DiscountType discountType;

    public Discount(DiscountType discountType,
                    String discountCode,
                    int discountAmount) {
        this.discountType = discountType;
        this.discountCode = discountCode;
        this.discountAmount = discountAmount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

}
