package com.topakkaya.reading.service;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.model.CustomerDTO;

public interface ICustomerService {
    Customer createCustomer(CustomerDTO customerDTO);

    Customer findCustomer(Long customerId);
}
