package observer;

import data.Customer;
import data.Shop;
import exceptions.ItemNotFoundException;
import exceptions.NotEnoughItemsToSellException;


import java.io.IOException;

public interface ISellNotify {
    void remove(ISellObserver obs) throws IOException;
    void add(ISellObserver obs) throws IOException;
    double OnSell(String typeItem, String countItems, Customer customer, Shop shop) throws NotEnoughItemsToSellException,IOException, ItemNotFoundException;

}
