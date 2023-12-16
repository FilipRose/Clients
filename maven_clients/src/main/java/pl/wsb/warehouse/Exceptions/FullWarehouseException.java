package pl.wsb.warehouse.Exceptions;

public class FullWarehouseException extends RuntimeException {
    public FullWarehouseException(String message) {
        super(message);
    }
}
