package com.Ecommmerce.exceptions;

public class PentazonException extends Exception{

    public PentazonException() {
        super();
    }

    public PentazonException(String message) {
        super(message);
    }

    public PentazonException(String message, Throwable cause) {
        super(message, cause);
    }
    public PentazonException(Throwable cause) {
        super(cause);
    }
}