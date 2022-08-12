package com.dmdev.natalliavasilyeva.service.exception;

import com.dmdev.natalliavasilyeva.domain.model.ErrorCode;

public class DriverLicenseBadRequestException extends CustomException {

    public DriverLicenseBadRequestException() {
        super();
    }

    public DriverLicenseBadRequestException(String message) {
        super(message);
    }

    public DriverLicenseBadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}