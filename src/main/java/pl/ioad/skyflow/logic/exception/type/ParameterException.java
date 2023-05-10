package pl.ioad.skyflow.logic.exception.type;

public class ParameterException extends RuntimeException {
    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(Exception e) {
        super(e);
    }
}