package com.detect.mutant.service.stats;

import com.detect.mutant.controller.dto.Statistics;


public interface IStatsService {
    Statistics getStats();
    long countMutant();
    long countHuman();

}
