package com.example.stocks.controller;

import com.example.stocks.dto.*;
import com.example.stocks.rep.StockRepository;
import com.example.stocks.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;
    private final StockRepository repository;

    @PostMapping("/create")
    public ResponseEntity<Integer> createStock(@RequestBody @Valid StockRequest request) {
        return ResponseEntity.ok(service.createStock(request));
    }

    @PostMapping("/create/category")
    public ResponseEntity<Integer> createStockCategory(@RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok(service.createStockCategory(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<StocksPurchaseResponse>> purchaseStocks(@RequestBody List<StocksPurchaseRequest> request) {
        return ResponseEntity.ok(service.purchaseStocks(request));

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<StockResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size) {
        var totalElements = repository.count();
        var response = service.findAll(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<StockResponse> pageResponse = new PageImpl<>(
                response, pageable, totalElements
        );
        return ResponseEntity.ok(pageResponse);
    }
}
