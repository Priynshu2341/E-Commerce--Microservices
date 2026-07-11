package com.example.stock_service.repository;


import com.example.stock_service.rep.CategoryRepository;
import com.example.stock_service.rep.StockRepository;
import com.example.stock_service.stock.Category;
import com.example.stock_service.stock.Stocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.util.List;


@DataJpaTest
@Testcontainers
public class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17.6").withReuse(true);

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void shouldClearDatabase() {
        stockRepository.deleteAll();
        categoryRepository.deleteAll();
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

    @Test
    void shouldFindAllStockById(){
        Category category = Category
                .builder()
                .name("phone")
                .description("flagship phone")
                .build();

        categoryRepository.save(category);
        Stocks stock1 = Stocks
                .builder()
                .name("phone")
                .description("flagship phone")
                .price(BigDecimal.valueOf(1500))
                .availableQuantity(1500)
                .category(category)
                .build();

        Stocks stock2 = Stocks
                .builder()
                .name("iPhone")
                .description("flagship phone")
                .price(BigDecimal.valueOf(1500))
                .availableQuantity(1500)
                .category(category)
                .build();

        Stocks stock3 = Stocks
                .builder()
                .name("phone")
                .description("flagship phone")
                .price(BigDecimal.valueOf(1500))
                .availableQuantity(1500)
                .category(category)
                .build();


        Stocks saved = stockRepository.save(stock1);
        Stocks saved2 = stockRepository.save(stock2);
        Stocks saved3 = stockRepository.save(stock3);

        List<Integer> productIds = List.of(saved.getId(), saved2.getId(), saved3.getId());
        List<Stocks> storedProduct = stockRepository.findAllByIdInOrderById(productIds);
        Assertions.assertNotNull(storedProduct);
        Assertions.assertEquals(storedProduct.size(), productIds.size());
        Assertions.assertEquals(storedProduct.get(0).getId(), productIds.get(0));
        Assertions.assertEquals(storedProduct.get(1).getId(), productIds.get(1));
        Assertions.assertEquals(storedProduct.get(2).getId(), productIds.get(2));
        Assertions.assertEquals("phone",storedProduct.get(0).getName());
        Assertions.assertEquals("flagship phone",storedProduct.get(1).getDescription());
        Assertions.assertEquals("iPhone",storedProduct.get(1).getName());
        Assertions.assertEquals("phone",storedProduct.get(2).getName());



    }
}
