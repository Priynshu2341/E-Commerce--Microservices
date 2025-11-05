package com.example.stocks.rep;

import com.example.stocks.dto.StocksPurchaseResponse;
import com.example.stocks.stock.Stocks;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stocks,Integer> {

    List<Stocks> findAllByIdInOrderById(List<Integer> productIds);
}
