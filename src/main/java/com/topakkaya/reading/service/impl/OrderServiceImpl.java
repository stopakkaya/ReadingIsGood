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
import com.topakkaya.reading.service.IOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final ICustomerService customerService;
    private final IBookService bookService;
    private final CustomerOrderRepository orderRepository;
    private final OrderMapper mapper;

    @Override
    public Page<OrderDTO> getCustomerOrders(Pageable pageable, Long customerId) {
        customerService.findCustomer(customerId);
        Page<Order> customerPage = orderRepository.getCustomerOrdersByCustomerId(customerId, pageable);
        List<OrderDTO> orderDTOList = mapper.toDtoList(customerPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, customerPage.getTotalElements());
    }

    @Override
    public void createOrder(OrderDTO orderDTO) {
        Customer customer = customerService.findCustomer(orderDTO.getCustomerId());
        BookDTO bookDTO = bookService.getBookById(orderDTO.getBookId());
        decreaseStock(bookDTO.getId(), orderDTO.getOrderAmount());
        Order order = mapper.toEntity(orderDTO, customer);
        order.setTotalPurchasedAmount(bookDTO.getPrice() * orderDTO.getOrderAmount());
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public void decreaseStock(Long bookId, int amount) {
        try {
            bookService.decreaseStock(bookId, amount);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.warn("Somebody has already updated the amount for item:{} in concurrent transaction. Will try again...", bookId);
            bookService.decreaseStock(bookId, amount);
        }
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
        return mapper.toDto(order);
    }

    @Override
    public List<OrderDTO> getOrderByDateInterval(OrderQueryDTO queryDTO) {
        List<Order> orderList = orderRepository.getAllByOrderDateBetween(queryDTO.getStartDate(), queryDTO.getEndDate());
        return mapper.toDtoList(orderList);
    }

}
