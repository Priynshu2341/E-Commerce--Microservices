package com.example.stocks.service;

import com.example.stocks.dto.*;
import com.example.stocks.hadler.StockNotFoundException;
import com.example.stocks.rep.CategoryRepository;
import com.example.stocks.rep.StockRepository;
import com.example.stocks.stock.Category;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository repository;
    private final StockMapper mapper;
    private final CategoryRepository categoryRepository;


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

    public List<StockResponse> findAll(int page,int size) {
        return repository.findAll(PageRequest.of(page,size))
                .stream()
                .map(mapper::toStockResponse)
                .toList();
    }

    public Integer createStockCategory(@Valid CategoryRequest request) {
        var category = mapper.toCategory(request);
        categoryRepository.save(category);
        return category.getId();

    }
}
