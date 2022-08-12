package com.dmdev.natalliavasilyeva.service.exception;

import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;

public class BrandBadRequestException extends CustomException {
    public BrandBadRequestException() {
        super();
    }

    public BrandBadRequestException(String message) {
        super(message);
    }
    public BrandBadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}