package com.example.stock_service.integration;

import com.example.stock_service.dto.StockRequest;
import com.example.stock_service.dto.StocksPurchaseRequest;
import com.example.stock_service.rep.CategoryRepository;
import com.example.stock_service.rep.StockRepository;
import com.example.stock_service.stock.Category;
import com.example.stock_service.stock.Stocks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class StockIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    private static final PostgreSQLContainer<?>  postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:17.6").withReuse(true);

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @BeforeEach
    void shouldClearDb() {
        stockRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldCreateCategory() throws Exception {
        String request = """
                {
            "name":"Phones",
            "description":"Flagship phones"
        }
      """;

        mockMvc.perform(post("/api/v1/stocks/create/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
                .andExpect(status().isCreated());


        Category saved = categoryRepository.findAll().getFirst();

        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Phones", saved.getName());
        Assertions.assertEquals("Flagship phones", saved.getDescription());
        Assertions.assertEquals(1, categoryRepository.count());

    }

    @Test
    void shouldCreateStock() throws Exception {
        String request = """
                {
            "name":"Phones",
            "description":"Flagship phones"
        }
      """;

        mockMvc.perform(post("/api/v1/stocks/create/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andExpect(status().isCreated());

        Category savedCategory = categoryRepository.findAll().getFirst();

        StockRequest stockRequest = new StockRequest(
          "iqoo 12",
          "flagship phone",
          1500,
          BigDecimal.valueOf(50000.0),
                savedCategory.getId()
        );

        mockMvc.perform(post("/api/v1/stocks/create")
                .content(objectMapper.writeValueAsString(stockRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        Stocks saved = stockRepository.findAll().getFirst();
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("iqoo 12", saved.getName());
        Assertions.assertEquals("flagship phone", saved.getDescription());
        Assertions.assertEquals(1500, saved.getAvailableQuantity());
        Assertions.assertEquals(0, BigDecimal.valueOf(50000.0).compareTo(saved.getPrice()));
        Assertions.assertEquals("Phones", saved.getCategory().getName());


    }

    @Test
    void shouldUpdateStock() throws Exception {
        String request = """
                {
            "name":"Phones",
            "description":"Flagship phones"
        }
      """;

        mockMvc.perform(post("/api/v1/stocks/create/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andExpect(status().isCreated());

        Category savedCategory = categoryRepository.findAll().getFirst();

        StockRequest stockRequest = new StockRequest(
                "iqoo 12",
                "flagship phone",
                1500,
                BigDecimal.valueOf(50000.0),
                savedCategory.getId()
        );

        mockMvc.perform(post("/api/v1/stocks/create")
                        .content(objectMapper.writeValueAsString(stockRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        Stocks saved = stockRepository.findAll().getFirst();

        StockRequest updateStock = new StockRequest(
                "iqoo 13",
                "flagship iphone",
                1500,
                BigDecimal.valueOf(50000.0),
                savedCategory.getId()
        );
        mockMvc.perform(put("/api/v1/stocks/update/" + saved.getId())
                .content(objectMapper.writeValueAsString(updateStock))
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        Stocks updated = stockRepository.findAll().getFirst();

        Assertions.assertNotNull(updated);
        Assertions.assertEquals("iqoo 13", updated.getName());
        Assertions.assertEquals("flagship iphone", updated.getDescription());



    }

    @Test
    void shouldPurchaseStock() throws Exception {

        String request = """
                {
            "name":"Phones",
            "description":"Flagship phones"
        }
      """;

        mockMvc.perform(post("/api/v1/stocks/create/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andExpect(status().isCreated());

        Category savedCategory = categoryRepository.findAll().getFirst();

        Stocks stock = Stocks.builder()
                .name("iPhone")
                .description("Apple phone")
                .price(BigDecimal.valueOf(450))
                .availableQuantity(20000)
                .category(savedCategory)
                .build();

        Stocks stock2 = Stocks.builder()
                .name("Iqoo 12")
                .description("Apple phone")
                .price(BigDecimal.valueOf(450))
                .availableQuantity(20000)
                .category(savedCategory)
                .build();


       Stocks saved1 =  stockRepository.save(stock);
       Stocks saved2 = stockRepository.save(stock2);

        StocksPurchaseRequest stocksPurchaseRequest = new StocksPurchaseRequest(
                saved1.getId(),
                20

        );

        StocksPurchaseRequest stocksPurchaseRequest2 = new StocksPurchaseRequest(
                saved2.getId(),
                100

        );

        List<StocksPurchaseRequest> requests = Arrays.asList(stocksPurchaseRequest,stocksPurchaseRequest2);

       mockMvc.perform(post("/api/v1/stocks/purchase")
               .content(objectMapper.writeValueAsString(requests))
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.[0].name").value(saved1.getName()))
               .andExpect(jsonPath("$.[1].name").value(saved2.getName()))
               .andExpect(jsonPath("$.[0].description").value(saved2.getDescription()))
               .andExpect(jsonPath("$.[1].description").value(saved1.getDescription()));


        Stocks updated1 = stockRepository.findById(saved1.getId()).orElseThrow();
        Stocks updated2 = stockRepository.findById(saved2.getId()).orElseThrow();

        Assertions.assertEquals(19980, updated1.getAvailableQuantity());
        Assertions.assertEquals(19900, updated2.getAvailableQuantity());


    }

}
