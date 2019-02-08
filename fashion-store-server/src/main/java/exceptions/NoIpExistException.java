package exceptions;

public class NoIpExistException extends ServerException {
    public NoIpExistException () {
        super("No Ip exist online");
    }
}
