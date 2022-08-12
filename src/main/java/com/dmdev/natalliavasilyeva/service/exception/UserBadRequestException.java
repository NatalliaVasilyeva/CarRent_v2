package com.dmdev.natalliavasilyeva.service.exception;

import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;

public class UserBadRequestException extends CustomException {

    public UserBadRequestException() {
        super();
    }

    public UserBadRequestException(String message) {
        super(message);
    }

    public UserBadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}