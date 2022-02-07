package com.topakkaya.reading.controller;

import com.topakkaya.reading.builder.ResponseBuilder;
import com.topakkaya.reading.enums.ReturnType;
import com.topakkaya.reading.exception.BookAlreadyExistException;
import com.topakkaya.reading.exception.CustomerAlreadyExistException;
import com.topakkaya.reading.model.CustomerDTO;
import com.topakkaya.reading.model.OrderDTO;
import com.topakkaya.reading.service.ICustomerService;
import com.topakkaya.reading.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/retail/v1/customer")
public class CustomerController {
    private final ICustomerService customerService;
    private final IOrderService orderService;

    /**
     * @author samet topakkaya
     * @apiNote persist new customer for given parameters
     * @param customerDTO consist customer infos
     * @throws CustomerAlreadyExistException customer is saved before (checks by author and bookName pair)
     */
    @PostMapping("/create-customer")
    public ResponseEntity<Map<String, Object>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        try {
            customerService.createCustomer(customerDTO);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).build();
        } catch (CustomerAlreadyExistException exception) {
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        } catch (Exception e) {
            return new ResponseBuilder(HttpStatus.BAD_REQUEST, ReturnType.FAIL).build();
        }
    }

    /**
     * @author samet topakkaya
     * @apiNote lists customer all orders pageable
     * @param pageable
     * @param customerId
     */
    @GetMapping("/customer-order/{customerId}")
    public ResponseEntity<Map<String, Object>> getCustomerOrders(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
                                                                 @PathVariable Long customerId) {
        Page<OrderDTO> customerOrders = orderService.getCustomerOrders(pageable, customerId);
        return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).withPagination(customerOrders).build();
    }
}
