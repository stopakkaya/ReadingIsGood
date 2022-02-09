package com.topakkaya.reading.service;

import com.topakkaya.reading.model.StatisticDTO;

import java.util.List;

public interface IStatisticService {
    List<StatisticDTO> getStatistics(String email);
}
