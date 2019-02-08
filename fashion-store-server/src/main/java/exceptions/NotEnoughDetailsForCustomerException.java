package exceptions;

public class NotEnoughDetailsForCustomerException extends ServerException {
    public NotEnoughDetailsForCustomerException () {
        super("Not enough details for customer");
    }
}
