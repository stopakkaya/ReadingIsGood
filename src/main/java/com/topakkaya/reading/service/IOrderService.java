package com.topakkaya.reading.service;

import com.topakkaya.reading.entity.Order;
import com.topakkaya.reading.model.OrderDTO;
import com.topakkaya.reading.model.OrderQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Page<OrderDTO> getCustomerOrders(Pageable pageable, Long customerId);

    Order createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long orderId);

    List<OrderDTO> getOrderByDateInterval(OrderQueryDTO queryDTO);
}
