package com.example.stocks.service;

import com.example.stocks.dto.StockRequest;
import com.example.stocks.dto.StockResponse;
import com.example.stocks.dto.StocksPurchaseRequest;
import com.example.stocks.dto.StocksPurchaseResponse;
import com.example.stocks.hadler.StockNotFoundException;
import com.example.stocks.rep.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository repository;
    private final StockMapper mapper;


    public Integer createStock(@Valid StockRequest request) {
        var stock = mapper.toStock(request);
        return repository.save(stock).getId();
    }


    public List<StocksPurchaseResponse> purchaseStocks(List<StocksPurchaseRequest> request) {
        var productIds = request
                .stream()
                .map(StocksPurchaseRequest::id)
                .toList();
        var storedProduct = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProduct.size()) {
            throw new StockNotFoundException("One or More Product Does not exist");
        }
        var storesRequest = request
                .stream()
                .sorted(Comparator.comparing(StocksPurchaseRequest::id))
                .toList();
        var purchasedProducts = new ArrayList<StocksPurchaseResponse>();

        for (int i = 0; i < storedProduct.size(); i++) {
            var product = storedProduct.get(i);
            var productRequest = storesRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new StockNotFoundException("One or More Product is out of Stock" + product.getId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toStockPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public StockResponse findById(Integer id) {
        return repository.findById(id).
                map(mapper::toStockResponse).
                orElseThrow(() -> new EntityNotFoundException(""));
    }

    public List<StockResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toStockResponse)
                .toList();
    }
}
