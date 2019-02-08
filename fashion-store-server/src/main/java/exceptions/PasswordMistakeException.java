package exceptions;

public class PasswordMistakeException extends ServerException {

    public PasswordMistakeException() {
        super("The password is wrong");
    }
}
