package data;

public class VIPCustomer extends Customer {

    public VIPCustomer() {}

    public VIPCustomer(ReturneeCustomer returneeCustomer) {
        name = returneeCustomer.getName();
        id = returneeCustomer.getID();
        phoneNumber = returneeCustomer.getPhoneNumber();
        sumOfPurchase = returneeCustomer.getSumOfPurchase();
        buysAmount = returneeCustomer.getBuysAmount();
        discount = 0.5 ;
    }

    @Override
    public String toString() {
        return "VIPCustomer" +super.toString();
    }
}

