package com.example.stock_service.rep;



import com.example.stock_service.stock.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stocks,Integer> {

    List<Stocks> findAllByIdInOrderById(List<Integer> productIds);
}
