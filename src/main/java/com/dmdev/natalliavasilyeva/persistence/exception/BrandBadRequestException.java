package com.dmdev.natalliavasilyeva.persistence.exception;

public class BrandBadRequestException extends RuntimeException {

    public BrandBadRequestException() {
        super();
    }

    public BrandBadRequestException(String message) {
        super(message);
    }
}