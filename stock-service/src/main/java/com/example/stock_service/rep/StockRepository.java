package com.example.stock_service.rep;



import com.example.stock_service.stock.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stocks,Integer> {

    @Query("""
       SELECT s
       FROM Stocks s
       WHERE s.id IN :productIds
       ORDER BY s.id
""")
    List<Stocks> findAllByIdInOrderById(@Param("productIds") List<Integer> productIds);
}
