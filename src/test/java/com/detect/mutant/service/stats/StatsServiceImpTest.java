package com.detect.mutant.service.stats;

import com.detect.mutant.controller.dto.Statistics;
import com.detect.mutant.repository.IHumanRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

class StatsServiceImpTest {

    private final IHumanRepository iHumanRepository = Mockito.mock(IHumanRepository.class);
    private StatsServiceImp statsServiceImp;
    private Statistics statisticsExpected;
    private Statistics statisticsExpectedNotHuman;


    @BeforeEach
    void setUp() {
        statsServiceImp = new StatsServiceImp(iHumanRepository);
        statisticsExpected = Statistics.builder()
                .countHumanDna(100)
                .countMutantDna(4)
                .ratio(0.04)
                .build();

        statisticsExpectedNotHuman = Statistics.builder()
                .countHumanDna(0)
                .countMutantDna(4)
                .ratio(0.0)
                .build();
    }

    @Test
    void getStats() throws Exception {
        when(iHumanRepository.countByIsMutantFalse()).thenReturn(100.0);
        when(iHumanRepository.countByIsMutantTrue()).thenReturn(4.0);
        Statistics statisticsActual = statsServiceImp.getStats();
        Assertions.assertEquals(statisticsExpected, statisticsActual);
    }

    @Test
    void getStatsNotHuman() throws Exception {
        when(iHumanRepository.countByIsMutantFalse()).thenReturn(0.0);
        when(iHumanRepository.countByIsMutantTrue()).thenReturn(4.0);

        System.out.println(statisticsExpected.toString());
        Statistics statisticsActual = statsServiceImp.getStats();
        Assertions.assertEquals(statisticsExpectedNotHuman, statisticsActual);
    }

}