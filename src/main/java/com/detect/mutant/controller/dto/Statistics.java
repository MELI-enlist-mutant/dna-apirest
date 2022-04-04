package com.detect.mutant.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Statistics {

    private long countMutantDna;
    private long countHumanDna;
    private double ratio;
}
