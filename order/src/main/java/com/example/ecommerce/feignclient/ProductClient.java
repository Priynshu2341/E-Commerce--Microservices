package com.example.ecommerce.feignclient;

import com.example.ecommerce.dtos.requestdtos.PurchaseRequest;
import com.example.ecommerce.dtos.responsedtos.PurchaseResponse;
import com.example.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {


    @Value("${application.config.stock-url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseResponses(List<PurchaseRequest> requests) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> httpEntity = new HttpEntity<>(requests, httpHeaders);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<List<PurchaseResponse>>() {
                };

        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                httpEntity,
                responseType

        );
        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred during purchase:" + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
}
