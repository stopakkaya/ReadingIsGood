package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.entity.Order;
import com.topakkaya.reading.exception.CustomerNotFoundException;
import com.topakkaya.reading.mapper.OrderMapper;
import com.topakkaya.reading.model.OrderDTO;
import com.topakkaya.reading.repository.CustomerOrderRepository;
import com.topakkaya.reading.service.ICustomerService;
import com.topakkaya.reading.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final ICustomerService customerService;
    private final CustomerOrderRepository orderRepository;
    private final OrderMapper mapper;

    @Override
    public Page<OrderDTO> getCustomerOrders(Pageable pageable, Long customerId) {
        Customer customer = customerService.findCustomer(customerId);
        if(Objects.isNull(customer))
            throw new CustomerNotFoundException();

        Page<Order> customerPage = orderRepository.getCustomerOrdersByCustomerId(customerId, pageable);
        List<OrderDTO> orderDTOList = mapper.toDtoList(customerPage.getContent());
        return new PageImpl<>(orderDTOList, pageable,customerPage.getTotalElements());
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {
        Customer customer = customerService.findCustomer(orderDTO.getCustomerId());
        if(Objects.isNull(customer))
            throw new CustomerNotFoundException();
        Order order = mapper.toEntity(orderDTO, customer);
        orderRepository.save(order);
    }
}
