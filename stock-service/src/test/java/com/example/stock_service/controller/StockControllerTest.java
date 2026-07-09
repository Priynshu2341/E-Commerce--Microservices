package com.example.stock_service.controller;

import com.example.stock_service.dto.*;
import com.example.stock_service.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockService stockService;


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ServerProperties serverProperties;


    @Test
    void shouldCreateCategory() throws Exception {
        CategoryRequest request = new CategoryRequest(
                "phones",
                "flagship phones"
        );
        when(stockService.createStockCategory(request)).thenReturn(1);

     mockMvc.perform(post("/api/v1/stocks/create/category")
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(request)))
             .andExpect(status().isCreated())
             .andExpect(content().string("1"));

     verify(stockService).createStockCategory(request);
     verifyNoMoreInteractions(stockService);


    }

    @Test
    void shouldCreateStock() throws Exception {
        StockRequest request = new StockRequest(
                "iPhone",
                "Apple phone",
                450,
                BigDecimal.valueOf(20000),
                1
        );

        when(stockService.createStock(request)).thenReturn(1);

        mockMvc.perform(post("/api/v1/stocks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(stockService).createStock(request);
        verifyNoMoreInteractions(stockService);



    }

    @Test
    void shouldPurchaseStocks() throws Exception {
        StocksPurchaseRequest request1 = new StocksPurchaseRequest(1, 10);
        StocksPurchaseRequest request2 = new StocksPurchaseRequest(2, 5);

        StocksPurchaseResponse response1 = new StocksPurchaseResponse(
                1,
                "iPhone",
                "flagship phone",
                BigDecimal.valueOf(500),
                10
        );

        StocksPurchaseResponse response2 = new StocksPurchaseResponse(
                2,
                "Samsung",
                "android flagship",
                BigDecimal.valueOf(400),
                5
        );

       when(stockService.purchaseStocks(List.of(request1,request2))).thenReturn(List.of(response1,response2));

       mockMvc.perform(post("/api/v1/stocks/purchase")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(List.of(request1,request2)))
       )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.[0].id").value(1))
               .andExpect(jsonPath("$.[1].id").value(2))
               .andExpect(jsonPath("$.[0].name").value("iPhone"))
               .andExpect(jsonPath("$.[1].name").value("Samsung"));

       verify(stockService).purchaseStocks(List.of(request1,request2));
       verifyNoMoreInteractions(stockService);
    }


    @Test
    void shouldFindAllStocks() throws Exception {
        StockResponse response = new StockResponse(
                1,
                "iPhone",
                "android flagship",
                5,
                BigDecimal.valueOf(20000),
                1,
                "iphone",
                "FlagShipDevice"
        );

        StockResponse response2 = new StockResponse(
                2,
                "Samsung",
                "android flagship",
                5,
                BigDecimal.valueOf(20000),
                1,
                "iphone",
                "FlagShipDevice"
        );

        StockResponse response3 = new StockResponse(
                3,
                "iphone",
                "android flagship",
                5,
                BigDecimal.valueOf(20000),
                1,
                "iphone",
                "FlagShipDevice"
        );

        when(stockService.countItemsInRepository()).thenReturn(3L);
        when(stockService.findAll(0,2)).thenReturn(List.of(response,response2));

        mockMvc.perform(get("/api/v1/stocks/findAll")
                .param("page","0")
                .param("size","2")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[0].name").value("iPhone"))
                .andExpect(jsonPath("$.content[1].name").value("Samsung"))
                .andExpect(jsonPath("$.content[0].description").value("android flagship"))
                .andExpect(jsonPath("$.content[0].availableQuantity").value(5))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(2));

        verify(stockService).countItemsInRepository();
        verify(stockService).findAll(0,2);
        verifyNoMoreInteractions(stockService);

    }


}
