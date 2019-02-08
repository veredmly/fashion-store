package exceptions;

public class PhoneNumberException extends ServerException {

    public PhoneNumberException() {
        super("The phone number is not correct");
    }
}
