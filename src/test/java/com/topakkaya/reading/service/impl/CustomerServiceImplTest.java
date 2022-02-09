package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.exception.CustomerAlreadyExistException;
import com.topakkaya.reading.exception.CustomerNotFoundException;
import com.topakkaya.reading.mapper.CustomerDTOMapper;
import com.topakkaya.reading.model.CustomerDTO;
import com.topakkaya.reading.repository.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CustomerServiceImpl.class)
public class CustomerServiceImplTest {
    CustomerRepository repository = Mockito.mock(CustomerRepository.class);
    CustomerDTOMapper mapper = Mockito.mock(CustomerDTOMapper.class);
    CustomerServiceImpl service = new CustomerServiceImpl(repository, mapper);

    @Test
    public void throwExceptionWhenCustomerAlreadyExistByEMail() {
        when(repository.findCustomerByEmail(anyString())).thenReturn(getCustomer());
        assertThrows(CustomerAlreadyExistException.class, () -> service.createCustomer(getCustomerDto()));
    }

    @Test
    public void createCustomer() {
        when(repository.findCustomerByEmail(anyString())).thenReturn(null);
        when(repository.save(any())).thenReturn(getCustomer());
        when(mapper.toCustomer(any())).thenReturn(getCustomer());
        Customer customer = service.createCustomer(getCustomerDto());
        Customer testCustomer = getCustomer();
        assertEquals(testCustomer, customer);
    }

    @Test
    public void throwExceptionWhenCustomerNotFoundById() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> service.findCustomer(1L));
    }

    @Test
    public void findCustomer() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getCustomer()));
        Customer customer = service.findCustomer(1L);
        Customer testCustomer = getCustomer();
        assertEquals(customer, testCustomer);
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setName("TestName");
        customer.setPassword("TestPassword");
        customer.setLastName("TestLastName");
        customer.setEmail("TestEmail");
        customer.setId(1L);
        return customer;
    }

    private CustomerDTO getCustomerDto() {
        return CustomerDTO.builder()
                .email("TestEmail")
                .name("TestName")
                .lastName("TestLastName")
                .password("TestPassword")
                .build();
    }
}