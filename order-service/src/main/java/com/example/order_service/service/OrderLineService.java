package com.example.order_service.service;


import com.example.order_service.dtos.requestdtos.OrderLineRequest;
import com.example.order_service.dtos.responsedtos.OrderLineResponse;
import com.example.order_service.rep.OrderLineRepository;
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
