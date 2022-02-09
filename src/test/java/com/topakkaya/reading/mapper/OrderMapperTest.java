package com.topakkaya.reading.mapper;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.entity.Order;
import com.topakkaya.reading.model.OrderDTO;
import org.aspectj.weaver.ast.Or;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = OrderMapper.class)
public class OrderMapperTest {

    OrderMapper mapper = new OrderMapper();
    Order order;
    OrderDTO dto;
    Customer customer;

    @Before
    public void setUp(){
        order = new Order();
        order.setBookName("BookName");
        order.setOrderAmount(10);
        order.setOrderDate(new Date());
        order.setBookId(1L);
        order.setCustomer(new Customer());
        order.setUpdateDate(new Date());
        order.setOrderAmount(10);
        order.setTotalPurchasedAmount(10d);
        order.setCreateDate(new Date());

        dto = new OrderDTO();
        dto.setBookName("BookName");

        customer = new Customer();
        customer.setId(1L);
    }

    @Test
    public void toDto() {
        OrderDTO orderDTO = mapper.toDto(order);
        assertEquals(order.getOrderAmount(), orderDTO.getOrderAmount());
        assertEquals(order.getOrderDate(), orderDTO.getOrderDate());
        assertEquals(order.getDeliverDate(), orderDTO.getDeliverDate());
        assertEquals(order.getId(), orderDTO.getId());
        assertEquals(order.getBookId(), orderDTO.getBookId());
        assertEquals(order.getTotalPurchasedAmount(), orderDTO.getTotalPurchasedAmount());
    }

    @Test
    public void toDtoList() {
        List<OrderDTO> orderDTOList = mapper.toDtoList(List.of(order));
        assertEquals(1,orderDTOList.size());
    }

    @Test
    public void toEntity() {
        Order order = mapper.toEntity(dto, customer);
        assertEquals(dto.getBookName(), order.getBookName());

    }
}