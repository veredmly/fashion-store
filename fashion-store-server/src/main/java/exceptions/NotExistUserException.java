package exceptions;

public class NotExistUserException extends ServerException {
    public NotExistUserException(){
        super("The user does not exist in the system");
    }
}
