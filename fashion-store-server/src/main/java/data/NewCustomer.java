package data;

public class NewCustomer extends Customer {

    public NewCustomer() {
        discount = 0 ;
    }

    @Override
    public String toString() {
        return "NewCustomer" +super.toString();
    }

    //if the customer buy almost one time we change his type for returnee
    public Customer changeRouteCustomer() {
        if (buysAmount == 1) {
            ReturneeCustomer returneeCustomer = new ReturneeCustomer(this);
            return returneeCustomer;
        }
        return this;
    }
}
