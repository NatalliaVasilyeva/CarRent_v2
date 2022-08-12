package com.dmdev.natalliavasilyeva.service.exception;

import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;

public class OrderBadRequestException extends CustomException {

    public OrderBadRequestException() {
        super();
    }

    public OrderBadRequestException(String message) {
        super(message);
    }

    public OrderBadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}