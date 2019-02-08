package exceptions;

public class UserAlreadyOnlineException extends ServerException {

    public UserAlreadyOnlineException() {
        super("This user is already online to the system");
    }
}
