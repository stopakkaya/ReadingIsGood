package com.topakkaya.reading.service.impl;

import com.topakkaya.reading.entity.Customer;
import com.topakkaya.reading.entity.Order;
import com.topakkaya.reading.exception.CustomerNotFoundException;
import com.topakkaya.reading.model.StatisticDTO;
import com.topakkaya.reading.repository.CustomerOrderRepository;
import com.topakkaya.reading.repository.CustomerRepository;
import com.topakkaya.reading.service.IStatisticService;
import com.topakkaya.reading.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements IStatisticService {
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository orderRepository;

    /**
     * @return user monthly statistic list by email - order list with month name
     * @author samet topakkaya
     */
    @Override
    public List<StatisticDTO> getStatistics(String customerEmail) {

        Customer customerByEmail = customerRepository.findCustomerByEmail(customerEmail);
        if (Objects.isNull(customerByEmail))
            throw new CustomerNotFoundException();

        List<Order> orders = orderRepository.getCustomerOrdersByCustomer(customerByEmail.getId());
        if (orders.isEmpty()) return new ArrayList<>();

        List<StatisticDTO> statisticDTOList = new ArrayList<>();

        orders.forEach(p -> {
            String monthName = DateUtil.getMonthName(p.getOrderDate());
            StatisticDTO dto = new StatisticDTO(monthName, null, p.getOrderAmount(), p.getTotalPurchasedAmount());
            statisticDTOList.add(dto);
        });

        Map<String, List<StatisticDTO>> averageLikesPerType = statisticDTOList.stream()
                .collect(groupingBy(StatisticDTO::getMonth, Collectors.toList()));
        List<StatisticDTO> responseList = new ArrayList<>();

        String[] monthNames = DateUtil.getMonths();

        for (String month : monthNames) {
            List<StatisticDTO> dtoList = averageLikesPerType.get(month);
            if (dtoList == null || dtoList.isEmpty()) continue;

            Double totalPurchasedAmount = dtoList.stream().mapToDouble(StatisticDTO::getTotalPurchasedAmount).sum();
            Integer totalBookCount = dtoList.stream().mapToInt(StatisticDTO::getTotalBookCount).sum();
            Integer totalOrderCount = dtoList.size();
            StatisticDTO dto = new StatisticDTO(month, totalOrderCount, totalBookCount, totalPurchasedAmount);
            responseList.add(dto);
        }
        return responseList;
    }
}
