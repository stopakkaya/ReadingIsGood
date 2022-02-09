package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.exception.CustomerAlreadyExistException;
import com.topakkaya.reading.exception.CustomerNotFoundException;
import com.topakkaya.reading.mapper.CustomerDTOMapper;
import com.topakkaya.reading.model.CustomerDTO;
import com.topakkaya.reading.repository.CustomerRepository;
import com.topakkaya.reading.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDTOMapper mapper;

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        log.info("Customer is creating.. email : {}", customerDTO.getEmail());
        Customer foundCustomer = customerRepository.findCustomerByEmail(customerDTO.getEmail());
        if (!Objects.isNull(foundCustomer))
            throw new CustomerAlreadyExistException();

        Customer customer = mapper.toCustomer(customerDTO);
        Customer saved = customerRepository.save(customer);
        log.info("Customer created successfully. email : {}", customerDTO.getEmail());
        return saved;
    }

    @Override
    public Customer findCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

}
