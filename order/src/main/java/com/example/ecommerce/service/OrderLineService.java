package com.example.ecommerce.service;

import com.example.ecommerce.dtos.responsedtos.OrderLineResponse;
import com.example.ecommerce.rep.OrderLineRepository;
import com.example.ecommerce.dtos.requestdtos.OrderLineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var order = orderLineMapper.toOrder(orderLineRequest);
        return orderLineRepository.save(order).getId();
    }

    public List<OrderLineResponse> findByOrderId(Integer id) {
        return orderLineRepository.findAllByOrderId(id)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .toList();
    }
}
