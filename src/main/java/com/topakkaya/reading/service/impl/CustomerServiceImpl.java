package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.exception.CustomerAlreadyExistException;
import com.topakkaya.reading.exception.CustomerNotFoundException;
import com.topakkaya.reading.mapper.CustomerDTOMapper;
import com.topakkaya.reading.model.CustomerDTO;
import com.topakkaya.reading.repository.CustomerRepository;
import com.topakkaya.reading.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerDao;
    private final CustomerDTOMapper mapper;

    @Override
    public void createCustomer(CustomerDTO customerDTO) {
        Customer foundCustomer = customerDao.findCustomerByEmail(customerDTO.getEmail());
        if(!Objects.isNull(foundCustomer))
            throw new CustomerAlreadyExistException();

        Customer customer = mapper.toCustomer(customerDTO);
        customerDao.save(customer);
    }

    @Override
    public Customer findCustomer(Long customerId) {
        return customerDao.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

}
