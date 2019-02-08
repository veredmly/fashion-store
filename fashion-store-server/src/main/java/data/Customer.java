package data;

import exceptions.IDException;
import exceptions.NoDetailsForCustomerException;
import exceptions.NotEnoughDetailsForCustomerException;
import exceptions.PhoneNumberException;

import java.util.Objects;

public abstract class Customer {

    protected String name;
    protected String id;
    protected String phoneNumber;
    protected double sumOfPurchase; // money
    protected int buysAmount;
    protected double discount;

    public Customer() {
        sumOfPurchase = 0;
        buysAmount = 0;
    }
    @Override
    public String toString() {
        return "{" +
                "name=" + name +
                ",id=" + id +
                ",phoneNumber=" + phoneNumber +
                ",purchaseAmount=" + sumOfPurchase +
                ",buysSum=" + buysAmount +
                ",discount=" + discount +
                '}';
    }

    public double priceAfterDiscount (double totalPrice) {
        return totalPrice *(1-discount);
    }

    //create new  customer by default
    public static Customer CreateAndUpdateCustomer(String i_Data) throws NoDetailsForCustomerException, NotEnoughDetailsForCustomerException, IDException, PhoneNumberException {
        Customer customer = new NewCustomer();
        if (i_Data == "" || i_Data == null) {
            throw new NoDetailsForCustomerException();
        }
        String[] details = i_Data.split(",");
        if (details.length <3) {
            throw new NotEnoughDetailsForCustomerException();
        }
        customer.setID(details[0]);
        customer.setName(details[1]);
        customer.setPhoneNumber(details[2]);
        return customer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return sumOfPurchase == customer.sumOfPurchase &&
                Double.compare(customer.buysAmount, buysAmount) == 0 &&
                Double.compare(customer.discount, discount) == 0 &&
                Objects.equals(name, customer.name) &&
                Objects.equals(id, customer.id) &&
                Objects.equals(phoneNumber, customer.phoneNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id, phoneNumber, sumOfPurchase, buysAmount, discount);
    }

    public void UpdateSumOfPurchase(double i_purchaseSum) {
        sumOfPurchase += i_purchaseSum;
    }

    public void updateBuysAmount () {
        buysAmount ++;

    }
    public String getID() {
        return id;
    }

    public void setID(String i_id) throws IDException {
        if (i_id.length() == 9)
            id = i_id;
        else
            throw new IDException();
    }

    public String getName() { return name; }

    public void setName(String i_name) { name = i_name; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String i_phoneNumber) throws PhoneNumberException {
        if (i_phoneNumber.length()<9)
            throw new PhoneNumberException();
        else
            phoneNumber = i_phoneNumber; }


    public double getSumOfPurchase() {
        return sumOfPurchase;
    }

    public int getBuysAmount() {
        return buysAmount;
    }

    public double getDiscount() {
        return discount;
    }
}