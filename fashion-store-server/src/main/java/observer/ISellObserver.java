package observer;

import exceptions.ItemNotFoundException;
import exceptions.NotEnoughItemsToSellException;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface ISellObserver {
    int sell(String typeItem, int count) throws IOException, NotEnoughItemsToSellException, ItemNotFoundException;
    int itemAmount(String typeItem) throws IOException, ItemNotFoundException;
    double checkItemPrice(String typeItem) throws IOException, ItemNotFoundException;
}
