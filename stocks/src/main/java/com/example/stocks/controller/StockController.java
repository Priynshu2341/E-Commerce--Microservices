package com.example.stocks.controller;

import com.example.stocks.dto.StockRequest;
import com.example.stocks.dto.StockResponse;
import com.example.stocks.dto.StocksPurchaseRequest;
import com.example.stocks.dto.StocksPurchaseResponse;
import com.example.stocks.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @PostMapping("/create")
    public ResponseEntity<Integer> createStock(@RequestBody @Valid StockRequest request) {
        return ResponseEntity.ok(service.createStock(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<StocksPurchaseResponse>> purchaseStocks(@RequestBody List<StocksPurchaseRequest> request) {
        return ResponseEntity.ok(service.purchaseStocks(request));

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<StockResponse> findById(@PathVariable Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<StockResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
}
