package pl.wsb.warehouse.Exceptions;

public class ProhibitedMetalTypeException extends RuntimeException {
    public ProhibitedMetalTypeException(String message) {
        super(message);
    }
}
