package com.example.ecommerce.dtos.responsedtos;

public record OrderCreationResponse<T> (
        String message,
        T data
){
}
