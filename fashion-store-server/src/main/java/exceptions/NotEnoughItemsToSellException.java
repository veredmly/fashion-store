package exceptions;

public class NotEnoughItemsToSellException extends ServerException {

    public NotEnoughItemsToSellException (){
        super("no enough items for this sell");
    }
}
