package com.dmdev.natalliavasilyeva.service.exception;

import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;

public class CustomException extends RuntimeException {

    private Integer errorCode;
    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode.getCode();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}