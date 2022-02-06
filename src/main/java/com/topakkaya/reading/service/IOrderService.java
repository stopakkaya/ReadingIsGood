package com.topakkaya.reading.service;

import com.topakkaya.reading.model.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Page<OrderDTO> getCustomerOrders(Pageable pageable, Long customerId);

    void createOrder(OrderDTO orderDTO);
}
