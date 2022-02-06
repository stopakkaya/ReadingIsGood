package com.topakkaya.reading.controller;

import com.topakkaya.reading.builder.ResponseBuilder;
import com.topakkaya.reading.enums.ReturnType;
import com.topakkaya.reading.exception.BookNotFoundException;
import com.topakkaya.reading.exception.CustomerNotFoundException;
import com.topakkaya.reading.exception.ExceedStockSizeException;
import com.topakkaya.reading.exception.OrderNotFoundException;
import com.topakkaya.reading.model.OrderDTO;
import com.topakkaya.reading.model.OrderQueryDTO;
import com.topakkaya.reading.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/retail/v1/order")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            orderService.createOrder(orderDTO);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).build();
        } catch (CustomerNotFoundException | BookNotFoundException | ExceedStockSizeException exception) {
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.getOrderById(orderId);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).withData(order).build();
        } catch (OrderNotFoundException exception) {
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        }
    }

    @GetMapping("/query-order")
    public ResponseEntity<Map<String, Object>> getOrderByDateInterval(@Valid @RequestBody OrderQueryDTO queryDTO) {
        List<OrderDTO> orderDTOList = orderService.getOrderByDateInterval(queryDTO);
        return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).withData(orderDTOList).build();
    }
}
