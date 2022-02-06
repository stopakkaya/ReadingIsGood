package com.topakkaya.reading.repository;

import com.topakkaya.reading.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderRepository extends JpaRepository<Order, Long> {
    @Query(value="SELECT * FROM customer_order WHERE customer_id = ?1 order by id", nativeQuery=true)
    Page<Order> getCustomerOrdersByCustomerId(Long customerId, Pageable pageable);
}
