package com.example.stock_service.exception;


import com.example.stock_service.controller.StockController;
import com.example.stock_service.hadler.GlobalExceptionHandler;
import com.example.stock_service.hadler.StockNotFoundException;
import com.example.stock_service.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockController.class)
@Import(GlobalExceptionHandler.class)
public class StockExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockService service;



    @Test
    void shouldReturn404WhenStockNotFound() throws Exception {
        when(service.findById(any())).thenThrow(new StockNotFoundException("Stock Not Found"));

        mockMvc.perform(get("/api/v1/stocks/find/123"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Stock Not Found"));
    }
}
