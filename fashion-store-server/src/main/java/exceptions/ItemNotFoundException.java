package exceptions;

public class ItemNotFoundException extends ServerException {

    public ItemNotFoundException() {
        super("Item is not found in inventory");
    }
}
