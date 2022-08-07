package com.dmdev.natalliavasilyeva.service.exception;

import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;

public class ServiceException extends CustomException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}