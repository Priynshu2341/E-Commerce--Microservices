package com.example.ecommerce.dtos.responsedtos;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {

}
