package exceptions;

public class AgeException extends ServerException {

    public AgeException() {
        super("Age must be at least 16 years old");
    }
}
