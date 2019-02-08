package data;

import java.util.ArrayList;
import java.util.List;

public class Chain {
    private List<Customer> customers;
    private List<Shop> shops;

    public Chain() {
        customers = new ArrayList<Customer>();
        shops = new ArrayList<Shop>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Shop> getShops() {
        return shops;
    }

    //add shop for the list
    public void addShop(Shop i_shop) {
        shops.add(i_shop);
    }

    //add customer for the list
    public void addCustomer(Customer i_customer) {
        customers.add(i_customer);
    }

    public void removeShop (Shop i_shop) {
        shops.remove(i_shop);
    }

    public void removeCustomer (Customer i_customer) {
        customers.remove(i_customer);
    }

    //get the details of the customer
   public String getDetailsCustomer (String Id) {
        String details = "";
        for (Customer customer: customers) {
            if (customer.getID().equals(Id)) {
                details += Id +"," + customer.getName() +"," + getTypeCustomer(customer);
                return details;
            }
        }
        return details;
    }

    //check in the list if the customer exist
    public String ifExistCustomer (String Id) {
        String answer;
        for (Customer customer: customers) {
            if (customer.getID().equals(Id)) {
                return "yes";
            }
        }
        return "no";
    }


    public String getTypeCustomer (Customer customer) {
        if (customer instanceof NewCustomer) {
            return "NewCustomer";
        }
        else if (customer instanceof ReturneeCustomer) {
            return "ReturneeCustomer";
        }
        else {
            return "VIPCustomer";
        }
    }
}
