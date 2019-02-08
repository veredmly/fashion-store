package exceptions;

public class NotConnectedUserException extends ServerException {
    public NotConnectedUserException () {
        super("Not connected user");
    }
}
