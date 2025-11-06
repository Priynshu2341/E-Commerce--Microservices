package com.example.stocks.service;

import com.example.stocks.dto.*;
import com.example.stocks.stock.Category;
import com.example.stocks.stock.Stocks;
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
