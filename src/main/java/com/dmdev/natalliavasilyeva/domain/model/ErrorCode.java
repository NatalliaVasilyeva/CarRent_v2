package com.dmdev.natalliavasilyeva.domain.model;

public enum ErrorCode {

    VALIDATION_PARSE_ERROR(422),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    INTERNAL_SERVER_ERROR(500);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}