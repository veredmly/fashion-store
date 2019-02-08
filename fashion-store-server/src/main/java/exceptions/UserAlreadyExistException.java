package exceptions;

public class UserAlreadyExistException extends ServerException {
    public UserAlreadyExistException() {
        super("This employee already exit in the system");
    }
}
