package com.Ecommmerce.exceptions;

public class CheckOutException extends PentazonException{

    public CheckOutException() {
        super();
    }

    public CheckOutException(String message) {
        super(message);
    }

    public CheckOutException(String message, Throwable cause) {
        super(message, cause);
    }
    public CheckOutException(Throwable cause) {
        super(cause);
    }
}
