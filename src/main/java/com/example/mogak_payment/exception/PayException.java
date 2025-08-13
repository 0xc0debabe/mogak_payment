package com.example.mogak_payment.exception;

import lombok.Getter;

@Getter
public class PayException extends RuntimeException {

    private final ErrorCode errorCode;

    public PayException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

}