package com.topakkaya.reading.controller;

import com.topakkaya.reading.builder.ResponseBuilder;
import com.topakkaya.reading.enums.ReturnType;
import com.topakkaya.reading.exception.CustomerNotFoundException;
import com.topakkaya.reading.model.StatisticDTO;
import com.topakkaya.reading.service.IStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/retail/v1/statistic")
public class StatisticController {
    private final IStatisticService service;

    /**
     * @throws CustomerNotFoundException when customer is not found for given email
     * @author samet topakkaya
     * @apiNote lists monthly statistics of user by email address
     */
    @GetMapping("/{email}")
    public ResponseEntity<Map<String, Object>> getStatistic(@PathVariable String email) {
        try {
            List<StatisticDTO> statistics = service.getStatistics(email);
            return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS).withData(statistics).build();
        } catch (CustomerNotFoundException exception) {
            return new ResponseBuilder(exception.getHttpStatus(), ReturnType.FAIL).withError(exception.getMessage()).build();
        }
    }
}
