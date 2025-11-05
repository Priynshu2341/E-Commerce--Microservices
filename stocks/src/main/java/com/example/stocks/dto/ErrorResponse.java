package com.example.stocks.dto;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {

}

