package exceptions;

public class IDException extends ServerException {

    public IDException() {
        super("ID is not correct");
    }
}
