package com.topakkaya.reading;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableSwagger2
public class ReadingApplication {

    @Autowired
    private CustomerRepository customerRepo;

    @PostConstruct
    public void initCustomers() {
        List<Customer> customers = Stream.of(
                        new Customer(101L, "samet", "topakkaya", "samettopakkaya@gmail.com", "password123", null),
                        new Customer(102L, "test", "test", "testuser@gmail.com", "password123", null)
                )
                .collect(Collectors.toList());
        customerRepo.saveAll(customers);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReadingApplication.class, args);
    }

}
