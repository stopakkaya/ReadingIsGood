package com.topakkaya.reading.service;

import com.topakkaya.reading.model.StatisticDTO;

import java.util.List;
import java.util.Map;

public interface IStatisticService {
    List<StatisticDTO> getStatistics(String email);
}
