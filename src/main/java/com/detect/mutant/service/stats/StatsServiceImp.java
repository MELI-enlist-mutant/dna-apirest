package com.detect.mutant.service.stats;

import com.detect.mutant.controller.dto.Statistics;
import com.detect.mutant.repository.IHumanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class StatsServiceImp implements IStatsService {
    private IHumanRepository iHumanRepository;

    @Override
    public Statistics getStats(){

        double countHuman = countHuman();
        double countMutant = countMutant();
        double ratio = countHuman > 0 ? countMutant / countHuman:0.0;

        return new Statistics().toBuilder()
                .countHumanDna((long) countHuman)
                .countMutantDna((long) countMutant)
                .ratio(ratio)
                .build();
    }

    @Override
    public long countMutant(){
        return (long) iHumanRepository.countByIsMutantTrue();
    }

    @Override
    public long countHuman(){
        return (long) iHumanRepository.countByIsMutantFalse();
    }
}
