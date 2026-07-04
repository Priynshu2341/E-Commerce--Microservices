package com.example.order_service.dtos.responsedtos;

public record OrderCreationResponse<T> (
        String message,
        T data
){
}
