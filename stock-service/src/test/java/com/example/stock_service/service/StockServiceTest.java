package com.example.stock_service.service;

import com.example.stock_service.dto.*;
import com.example.stock_service.hadler.StockNotFoundException;
import com.example.stock_service.rep.CategoryRepository;
import com.example.stock_service.rep.StockRepository;
import com.example.stock_service.stock.Category;
import com.example.stock_service.stock.Stocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @InjectMocks
    private StockService service;

    @Mock
    private StockMapper mapper;

    @Mock
    private StockRepository repository;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    void shouldCreateCategory(){
        CategoryRequest request = new CategoryRequest(
                "phones",
                "flagship Phones"

        );
        Category category = Category.builder()
                .id(1)
                .name("phones")
                .description("flagship Phones")
                .build();

        when(mapper.toCategory(request)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        Integer id = service.createStockCategory(request);

        assertEquals(1,id);

        verify(mapper).toCategory(request);
        verify(categoryRepository).save(category);
    }

    @Test
    void shouldCreateStocks() {
        StockRequest request = new StockRequest(
                "iPhone",
                "Apple phone",
                450,
                BigDecimal.valueOf(20000),
                1
        );

        Stocks stock = Stocks.builder()
                .id(10)
                .name("iPhone")
                .description("Apple phone")
                .price(BigDecimal.valueOf(450))
                .availableQuantity(20000)
                .build();

        when(mapper.toStock(request)).thenReturn(stock);
        when(repository.save(stock)).thenReturn(stock);

        Integer id = service.createStock(request);
        assertEquals(Integer.valueOf(10),id);
        verify(mapper).toStock(request);
        verify(repository).save(stock);

    }

    @Test
    void shouldPurchaseStock(){
        StocksPurchaseRequest request1 = new StocksPurchaseRequest(1, 10);
        StocksPurchaseRequest request2 = new StocksPurchaseRequest(2, 5);

        Stocks stock1 = Stocks.builder()
                .id(1)
                .name("iPhone")
                .availableQuantity(20)
                .price(BigDecimal.valueOf(500))
                .build();

        Stocks stock2 = Stocks.builder()
                .id(2)
                .name("Samsung")
                .availableQuantity(15)
                .price(BigDecimal.valueOf(400))
                .build();

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


        when(repository.findAllByIdInOrderById(List.of(1,2))).thenReturn(List.of(stock1,stock2));
        when(repository.save(any(Stocks.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.toStockPurchaseResponse(stock1,10)).thenReturn(response1);
        when(mapper.toStockPurchaseResponse(stock2,5)).thenReturn(response2);

        List<StocksPurchaseResponse> result = service.purchaseStocks(List.of(request1,request2));

        assertEquals(2,result.size());
        assertEquals(response1,result.get(0));
        assertEquals(response2,result.get(1));
        assertEquals(10,stock1.getAvailableQuantity());
        assertEquals(10,stock2.getAvailableQuantity());

        verify(repository,times(2)).save(any(Stocks.class));
        verify(mapper).toStockPurchaseResponse(stock1,10);
        verify(mapper).toStockPurchaseResponse(stock2,5);

    }


    @Test
    void shouldThrowProductNotFoundExceptionBecauseProductNotFound(){

        List<StocksPurchaseRequest> request = List.of(new StocksPurchaseRequest(1,5));
        when(repository.findAllByIdInOrderById(List.of(1))).thenReturn(List.of());
        assertThrows(StockNotFoundException.class,() -> service.purchaseStocks(request));
        verify(repository,never()).save(any());
    }

    @Test
    void shouldThrowProductNotFoundExceptionBecauseOutOfStock(){

        List<StocksPurchaseRequest> requests = List.of(new StocksPurchaseRequest(1,10));
        Stocks stock1 = Stocks.builder()
                .id(1)
                .name("iPhone")
                .availableQuantity(5)
                .price(BigDecimal.valueOf(500))
                .build();


        when(repository.findAllByIdInOrderById(List.of(1))).thenReturn(List.of(stock1));
        assertThrows(StockNotFoundException.class,() -> service.purchaseStocks(requests));
        verify(repository,never()).save(any());

    }

    @Test
    void shouldFindProductById(){
        Stocks stock = Stocks.builder()
                .id(1)
                .name("iPhone")
                .availableQuantity(5)
                .price(BigDecimal.valueOf(20000))
                .build();

        StockResponse response = new StockResponse(
                1,
                "iphone",
                "android flagship",
                5,
                BigDecimal.valueOf(20000),
                1,
                "iphone",
                "FlagShipDevice"
        );
        when(repository.findById(1)).thenReturn(Optional.of(stock));
        when(mapper.toStockResponse(stock)).thenReturn(response);
        StockResponse result = service.findById(1);

        assertEquals(1,result.id());
        assertEquals("iphone",result.name());
        assertEquals("android flagship",result.description());

        verify(repository).findById(1);
        verify(mapper).toStockResponse(stock);
        verifyNoMoreInteractions(repository,mapper);



    }


}
