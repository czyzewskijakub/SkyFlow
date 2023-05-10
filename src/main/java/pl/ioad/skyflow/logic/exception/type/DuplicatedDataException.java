package pl.ioad.skyflow.logic.exception.type;

public class DuplicatedDataException extends RuntimeException {

    public DuplicatedDataException(String message) {
        super(message);
    }

    public DuplicatedDataException(Exception e) {
        super(e);
    }
}