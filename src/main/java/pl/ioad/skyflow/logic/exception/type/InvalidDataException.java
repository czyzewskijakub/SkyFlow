package pl.ioad.skyflow.logic.exception.type;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(Exception e) {
        super(e);
    }
}
