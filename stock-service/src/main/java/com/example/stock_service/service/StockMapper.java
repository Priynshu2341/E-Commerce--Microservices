package com.example.stock_service.service;


import com.example.stock_service.dto.CategoryRequest;
import com.example.stock_service.dto.StockRequest;
import com.example.stock_service.dto.StockResponse;
import com.example.stock_service.dto.StocksPurchaseResponse;
import com.example.stock_service.stock.Category;
import com.example.stock_service.stock.Stocks;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockMapper {


    public Stocks toStock(@Valid StockRequest request) {
        return Stocks
                .builder()
                .id(request.id())
                .price(request.price())
                .availableQuantity(request.availableQuantity())
                .name(request.name())
                .description(request.description())
                .category(Category.
                        builder().
                        id(request.categoryId())
                        .build())
                .build();
    }

    public StockResponse toStockResponse(Stocks stocks) {
        return new StockResponse(
                stocks.getId(),
                stocks.getName(),
                stocks.getDescription(),
                stocks.getAvailableQuantity(),
                stocks.getPrice(),
                stocks.getCategory().getId(),
                stocks.getCategory().getName(),
                stocks.getCategory().getDescription()
        );
    }

    public StocksPurchaseResponse toStockPurchaseResponse(Stocks stocks, double quantity) {
        return new StocksPurchaseResponse(
                stocks.getId(),
                stocks.getName(),
                stocks.getDescription(),
                stocks.getPrice(),
                quantity
        );
    }

    public Category toCategory(@Valid CategoryRequest request) {
        return Category
                .builder()
                .name(request.name())
                .description(request.description())
                .build();
    }
}
