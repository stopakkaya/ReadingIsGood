package com.topakkaya.reading.mapper;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.entity.Order;
import com.topakkaya.reading.model.OrderDTO;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    public OrderDTO toDto(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setOrderDate(order.getOrderDate());
        dto.setDeliverDate(order.getDeliverDate());
        dto.setId(order.getId());
        dto.setBookName(order.getBookName());
        dto.setCustomerId(order.getCustomer().getId());
        return dto;
    }

    public List<OrderDTO> toDtoList(List<Order> orderList){
        List<OrderDTO> responseList = new ArrayList<>();
        orderList.forEach(order -> {
            responseList.add(toDto(order));
        });
        return responseList;
    }

    public Order toEntity(OrderDTO dto, Customer customer){
        Order order = new Order();
        order.setOrderDate(dto.getOrderDate());
        order.setDeliverDate(dto.getDeliverDate());
        order.setCustomer(customer);
        order.setCreateDate(Date.from(Instant.now()));
        order.setUpdateDate(Date.from(Instant.now()));
        order.setBookName(dto.getBookName());
        return order;
    }
}
