package com.hsbc.incident.common;

public class BizException extends RuntimeException {
    private Integer code;  // Business exception code
    private String message;  // Business exception message

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
