package com.example.order_service.dtos.responsedtos;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {

}
