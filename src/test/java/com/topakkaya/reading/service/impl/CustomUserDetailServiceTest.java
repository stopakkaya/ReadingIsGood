package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.repository.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CustomUserDetailService.class)
public class CustomUserDetailServiceTest {

    CustomerRepository repository = Mockito.mock(CustomerRepository.class);
    CustomUserDetailService service = new CustomUserDetailService(repository);

    @Test
    public void loadUserByUsername() {
        when(repository.findCustomerByEmail(anyString())).thenReturn(getCustomer());
        UserDetails userDetails = service.loadUserByUsername("test");
        Customer testCustomer = getCustomer();
        assertEquals(userDetails.getUsername(), testCustomer.getEmail());
        assertEquals(userDetails.getPassword(), testCustomer.getPassword());
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
}