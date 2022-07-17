package com.dmdev.natalliavasilyeva.persistence.exception;

public class PriceBadRequestException extends RuntimeException {

    public PriceBadRequestException() {
        super();
    }

    public PriceBadRequestException(String message) {
        super(message);
    }
}