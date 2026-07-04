package com.example.stock_service.dto;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {

}

