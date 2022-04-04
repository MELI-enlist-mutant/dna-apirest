package com.detect.mutant.controller;

import com.detect.mutant.controller.dto.DNA;
import com.detect.mutant.controller.dto.Statistics;
import com.detect.mutant.service.human.HumanServiceImp;
import com.detect.mutant.service.stats.StatsServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StatsControllerTest {

    private StatsController statsController;
    private final StatsServiceImp statsServiceImp = Mockito.mock(StatsServiceImp.class);
    private Statistics statsExpected;

    @BeforeEach
    void setUp() {
        statsController = new StatsController(statsServiceImp);
        statsExpected = Statistics.builder()
                .countHumanDna(100)
                .countMutantDna(4)
                .ratio(0.04)
                .build();
    }

    @Test
    void getStats() {
        when(statsServiceImp.getStats()).thenReturn(statsExpected);
        Statistics statsActual=statsController.getStats();
        Assertions.assertEquals(statsActual, statsExpected);
    }
}