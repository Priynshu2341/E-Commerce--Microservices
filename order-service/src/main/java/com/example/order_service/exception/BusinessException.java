package com.example.order_service.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String msg) {
        super(msg);
    }
}
