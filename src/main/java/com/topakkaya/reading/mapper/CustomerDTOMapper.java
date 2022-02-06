package com.topakkaya.reading.mapper;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.model.CustomerDTO;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@Component
public class CustomerDTOMapper {
    public Customer toCustomer(CustomerDTO dto){
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setMemberShipDate(Date.from(Instant.now()));
        return customer;
    }
}
