package pl.ioad.skyflow.logic.exception.type;

public class InvalidBusinessArgumentException extends RuntimeException {
    public InvalidBusinessArgumentException(String message) {
        super(message);
    }

    public InvalidBusinessArgumentException(Exception e) {
        super(e);
    }
}