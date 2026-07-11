package com.example.stock_service.repository;


import com.example.stock_service.rep.CategoryRepository;
import com.example.stock_service.rep.StockRepository;
import com.example.stock_service.stock.Category;
import com.example.stock_service.stock.Stocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

@DataJpaTest
@Testcontainers
public class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.6");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void shouldCreateCategory() {
        Category category = Category
                .builder()
                .name("phone")
                .description("flagship phone")
                .build();

        Category saved = categoryRepository.save(category);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(category.getName(), saved.getName());
        Assertions.assertEquals(category.getDescription(), saved.getDescription());}

    @Test
    void shouldCreateStock() {
        Category category = Category
                .builder()
                .name("phone")
                .description("flagship phone")
                .build();

        Stocks stocks = Stocks
                .builder()
                .name("phone")
                .description("flagship phone")
                .price(BigDecimal.valueOf(1500))
                .availableQuantity(1500)
                .category(category)
                .build();

        Stocks saved = stockRepository.save(stocks);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(stocks.getName(), saved.getName());
        Assertions.assertEquals(stocks.getDescription(), saved.getDescription());
        Assertions.assertEquals(stocks.getPrice(), saved.getPrice());
    }
}
