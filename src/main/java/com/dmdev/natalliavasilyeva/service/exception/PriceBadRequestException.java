package com.dmdev.natalliavasilyeva.service.exception;

import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;

public class PriceBadRequestException extends CustomException {

    public PriceBadRequestException() {
        super();
    }

    public PriceBadRequestException(String message) {
        super(message);
    }

    public PriceBadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}