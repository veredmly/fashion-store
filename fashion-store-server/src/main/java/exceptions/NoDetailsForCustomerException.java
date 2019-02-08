package exceptions;

public class NoDetailsForCustomerException extends ServerException {
    public NoDetailsForCustomerException () {
        super("No details for customer");
    }

}
