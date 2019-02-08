package data;

import exceptions.*;
import observer.ISellNotify;
import observer.ISellObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CashierEmployee extends SellerEmployee implements ISellNotify {
    
    final ArrayList<ISellObserver> sellObservers = new ArrayList<>();
    List <Customer> customers;
    public CashierEmployee() {
    }

    @Override
    public String toString() {
        return "CashierEmployee{" +
                "name=" + name +
                ",id=" + id +
                ",phone=" + phone +
                ",bankAccount=" + bankAccount +
                ",branch=" + branch +
                ",employeeNumber=" + employeeNumber +
                '}';
    }

    @Override
    public void remove(ISellObserver obs) {
        sellObservers.remove(obs);

    }

    @Override
    public void add(ISellObserver obs) {
        sellObservers.add(obs);
    }

    //the function that do sell for the customer and change in inventory
    public double OnSell(String items, String countOfItems,Customer customer,Shop shop) throws IOException, ItemNotFoundException, NotEnoughItemsToSellException, NotEnoughItemsToSellException, ItemNotFoundException {
        double sumOfPurchase = 0;
        if (sellObservers != null) {
            for (ISellObserver sellObserver : sellObservers) {
                String [] typeItems = items.split(",");
                String [] counts = countOfItems.split(",");
                for (int i=0; i<typeItems.length;i++) {
                    sumOfPurchase += sellObserver.sell(typeItems[i], Integer.parseInt(counts[i]));
                }
                sumOfPurchase *=(1-customer.getDiscount());
                shop.updateCountOfSells();
                shop.updateSumOfSells(sumOfPurchase);
                customer.UpdateSumOfPurchase(sumOfPurchase);
                customer.updateBuysAmount();

                if (customer instanceof NewCustomer) {
                    Customer returneeCustomer = ((NewCustomer) customer).changeRouteCustomer();
                    customers.remove(customer);
                    customers.add(returneeCustomer);
                }
                else if (customer instanceof ReturneeCustomer) {
                    Customer VipCustomer = ((ReturneeCustomer) customer).changeRouteCustomer();
                    customers.remove(customer);
                    customers.add(VipCustomer);
                }
            }
        }
        return sumOfPurchase;

    }

    //check if exist item
    public int OnExist(String typeItem) throws IOException,ItemNotFoundException {
        int ifExist=0;
        if (sellObservers != null) {
            for (ISellObserver sellObserver: sellObservers) {
                ifExist = sellObserver.itemAmount(typeItem);
            }
        }
        return ifExist;
    }

    //return the price
    public double OnPriceCheck(String typeItem) throws IOException,ItemNotFoundException{
        double itemPrice=0;
        if (sellObservers != null) {
            for (ISellObserver sellObserver: sellObservers) {
                itemPrice = sellObserver.checkItemPrice(typeItem);

            }
        }
        return itemPrice;
    }

    //create customer and add to the list
    public void createCustomer (String details) throws IDException, NotEnoughDetailsForCustomerException, NoDetailsForCustomerException, PhoneNumberException {
        Customer customer = Customer.CreateAndUpdateCustomer(details);
        customers.add(customer);
    }

    public void setCustomers (List<Customer> i_customers) {
        customers = i_customers;
    }

}
