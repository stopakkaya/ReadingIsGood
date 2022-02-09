package com.topakkaya.reading.mapper;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.model.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOMapper {
    public Customer toCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        return customer;
    }
}
