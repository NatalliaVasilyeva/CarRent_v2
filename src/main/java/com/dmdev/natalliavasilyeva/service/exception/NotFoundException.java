package com.dmdev.natalliavasilyeva.service.exception;

import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}