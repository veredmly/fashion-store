package data;

public class ReturneeCustomer extends Customer {

    public ReturneeCustomer() {}


    public ReturneeCustomer(NewCustomer newCustomer) {
        name = newCustomer.getName();
        id = newCustomer.getID();
        phoneNumber = newCustomer.getPhoneNumber();
        sumOfPurchase = newCustomer.getSumOfPurchase();
        buysAmount = newCustomer.getBuysAmount();
        discount = 0.2;
    }


    @Override
    public String toString() {
        return "ReturneeCustomer" +super.toString();
    }

    //if the customer buy in this condition we change him type
    public Customer changeRouteCustomer () {
        if ((buysAmount >2) && (sumOfPurchase >1000)) {
            VIPCustomer vipCustomer = new VIPCustomer(this);
            return vipCustomer;
        }
        return this;
    }
}
