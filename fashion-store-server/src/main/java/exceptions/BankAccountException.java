package exceptions;

public class BankAccountException extends ServerException {

    public BankAccountException() {
        super("Bank account number does not correct");
    }
}
