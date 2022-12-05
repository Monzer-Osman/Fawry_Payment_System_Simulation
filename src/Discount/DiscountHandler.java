package Discount;

import Data.DataBaseHandler;
import Exceptions.InvalidDiscountCode;
import Exceptions.InvalidDiscountType;
import java.util.ArrayList;

public class DiscountHandler {
    private DataBaseHandler dataBaseHandler;
    private ArrayList<Discount> discounts;

    public DiscountHandler(DataBaseHandler dataBaseHandler) {
        this.dataBaseHandler = dataBaseHandler;
        discounts = dataBaseHandler.getDiscounts();
    }

    public float getDiscountAmount(String discountCode, Discount.DiscountType discountType) throws Exception{
        isValidDiscountCode(discountCode);
        isValidDiscountType(discountType, discountCode);
        return getDiscountAmount(discountCode);
    }

    public boolean isValidDiscountCode(String discountCode) throws Exception{
        discounts = dataBaseHandler.getDiscounts();
        for (Discount discount : discounts) {
            if (discount.getDiscountCode().equalsIgnoreCase(discountCode)) {
                return true;
            }
        }
        throw new InvalidDiscountCode("Invalid Discount-Code");
    }

    public boolean isValidDiscountType(Discount.DiscountType discountType, String discountCode) throws Exception{
        discounts = dataBaseHandler.getDiscounts();
        for (Discount discount : discounts) {
            if(discount.getDiscountCode().equalsIgnoreCase(discountCode)) {
                if (discount.getDiscountType().equals(discountType) ||
                        discount.getDiscountType().equals(Discount.DiscountType.OverAll)) {
                    return true;
                }
                else {
                    throw new InvalidDiscountType("This Discount is not applicable in this Service");
                }
            }
        }
        throw new InvalidDiscountCode("Invalid Discount Code");
    }

    public float getDiscountAmount(String discountCode) {
        discounts = dataBaseHandler.getDiscounts();
        for(Discount discount: discounts){
            if(discount.getDiscountCode().equalsIgnoreCase(discountCode))
                return (float) discount.getDiscountAmount() / 100;
        }
        return 1;
    }

    public void displayAvailableDiscounts() {
        discounts = dataBaseHandler.getDiscounts();

        for(int i = 0; i < discounts.size(); i++) {
            System.out.println(i + 1 + "- " +
                    discounts.get(i).getDiscountAmount() +
                    "% Discount for " +
                    discounts.get(i).getDiscountType().toString() +
                    "\n Use the following Code to benefit the discount" +
                    " (" + discounts.get(i).getDiscountCode() + ")");
        }
    }

    public void addDiscount(Discount discount) {
        dataBaseHandler.addDiscount(discount);
    }

}
