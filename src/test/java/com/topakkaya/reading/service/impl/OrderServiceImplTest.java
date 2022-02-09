package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.entity.Order;
import com.topakkaya.reading.exception.OrderNotFoundException;
import com.topakkaya.reading.mapper.OrderMapper;
import com.topakkaya.reading.model.BookDTO;
import com.topakkaya.reading.model.OrderDTO;
import com.topakkaya.reading.model.OrderQueryDTO;
import com.topakkaya.reading.repository.CustomerOrderRepository;
import com.topakkaya.reading.service.IBookService;
import com.topakkaya.reading.service.ICustomerService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = OrderServiceImpl.class)
public class OrderServiceImplTest {
    ICustomerService customerService = Mockito.mock(ICustomerService.class);
    IBookService bookService = Mockito.mock(IBookService.class);
    CustomerOrderRepository orderRepository = Mockito.mock(CustomerOrderRepository.class);
    OrderMapper mapper = Mockito.mock(OrderMapper.class);
    OrderServiceImpl service = new OrderServiceImpl(customerService, bookService, orderRepository, mapper);

    @Test
    public void getCustomerOrders() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Order> orderList = List.of(getOrder());
        Page<Order> orderPage = new PageImpl<>(orderList, pageable, orderList.size());
        when(orderRepository.getCustomerOrdersByCustomerId(1L, pageable)).thenReturn(orderPage);
        when(mapper.toDtoList(orderList)).thenReturn(List.of(getOrderDto()));
        Page<OrderDTO> customerOrders = service.getCustomerOrders(pageable, 1L);
        assertEquals(customerOrders.getTotalElements(), 1);
        Integer currentOrderAmount = customerOrders.getContent().get(0).getOrderAmount();
        assertEquals(10, currentOrderAmount.intValue());

    }

    @Test
    public void createOrder() {
        when(customerService.findCustomer(any())).thenReturn(new Customer());
        when(bookService.getBookById(any())).thenReturn(BookDTO.builder().price(10d).build());
        when(mapper.toEntity(any(), any())).thenReturn(getOrder());
        when(orderRepository.save(any())).thenReturn(getOrder());
        Order order = service.createOrder(getOrderDto());
        assertEquals(order, getOrder());
    }

    @Test
    public void throwExceptionDecreaseStock() {
        when(bookService.decreaseStock(anyLong(), anyInt())).thenThrow(ObjectOptimisticLockingFailureException.class);
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> service.decreaseStock(1L, 1));
    }

    @Test
    public void decreaseStock() {
        service.decreaseStock(1L, 1);
    }

    @Test
    public void getOrderById() {
        when(orderRepository.findById(any())).thenReturn(Optional.of(getOrder()));
        when(mapper.toDto(any())).thenReturn(getOrderDto());
        OrderDTO dto = service.getOrderById(1L);
        assertEquals(getOrderDto(), dto);
    }

    @Test
    public void throwExceptionWhenOrderNotFound() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> service.getOrderById(1L));
    }

    @Test
    public void getOrderByDateInterval() {
        OrderQueryDTO queryDTO = new OrderQueryDTO();
        queryDTO.setEndDate(new Date());
        queryDTO.setStartDate(new Date());
        when(orderRepository.getAllByOrderDateBetween(any(), any())).thenReturn(List.of(getOrder()));
        when(mapper.toDtoList(any())).thenReturn(List.of(getOrderDto()));
        List<OrderDTO> responseList = service.getOrderByDateInterval(queryDTO);
        assertEquals(1, responseList.size());

    }

    private Order getOrder() {
        Order order = new Order();
        order.setOrderAmount(10);
        order.setTotalPurchasedAmount(100d);
        order.setBookId(1L);
        order.setBookName("TestBook");
        return order;
    }

    private OrderDTO getOrderDto() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderAmount(10);
        orderDTO.setBookId(1L);
        orderDTO.setBookName("TestBook");
        orderDTO.setTotalPurchasedAmount(100d);
        return orderDTO;
    }
}