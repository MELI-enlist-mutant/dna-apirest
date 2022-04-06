package com.detect.mutant.service.human;

import com.detect.mutant.controller.dto.DnaSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class MutantDetectTest {


    private MutantDetect mutantDetect;
    private List<String> dnaSmallList;
    private List<String> dnaMutantList;
    private List<String> dnaHumanList;
    private List<String> dnaMutant2lList;
    private List<String> dnaMutant3List;
    private List<String> dnaHumanBadList;


    @BeforeEach
    void setUp() {

        mutantDetect=new MutantDetect();

        DnaSequence dnaMutant = DnaSequence.builder()
                .dna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"})
                .build();
        DnaSequence dnaMutant2 = DnaSequence.builder()
                .dna(new String[]{"AAAAGA", "AAGTGC", "ATATGT", "AGAAGG", "CCCCTA", "TCACTG"})
                .build();
        DnaSequence dnaMutant3 = DnaSequence.builder()
                .dna(new String[]{"TTGCGA", "CACTGC", "TCATGT", "CGAAGG", "CTCCTA", "TCTATT"})
                .build();
        DnaSequence dnaHuman = DnaSequence.builder()
                .dna(new String[]{"TTGCGA", "CAGTGC", "TTATGT", "AGAAAG", "CTCCTA", "TCACTG"})
                .build();
        DnaSequence dnaHumanBad = DnaSequence.builder()
                .dna(new String[]{"TTGCGA", "CAGTCCC", "TTATGT", "AGAAGG", "CCCTTA", "TCACTG"})
                .build();
        DnaSequence dnaSmall = DnaSequence.builder()
                .dna(new String[]{"GTG", "CAG", "TTA"})
                .build();

        dnaSmallList = Arrays.asList(dnaSmall.getDna());
        dnaMutantList = Arrays.asList(dnaMutant.getDna());
        dnaHumanList = Arrays.asList(dnaHuman.getDna());
        dnaMutant2lList = Arrays.asList(dnaMutant2.getDna());
        dnaMutant3List = Arrays.asList(dnaMutant3.getDna());
        dnaHumanBadList = Arrays.asList(dnaHumanBad.getDna());

    }

    @Test
    void isDnaSmall() {
        boolean mutantDetectActual = mutantDetect.isMutant(dnaSmallList);
        Assertions.assertFalse(mutantDetectActual);

    }

    @Test
    void isMutant() {
        boolean mutantDetectActual = mutantDetect.isMutant(dnaMutantList);
        Assertions.assertTrue(mutantDetectActual);

    }

    @Test
    void isHuman() {
        boolean mutantDetectActual = mutantDetect.isMutant(dnaHumanList);
        Assertions.assertFalse(mutantDetectActual);

    }

    @Test
    void isMutant2() {
        boolean mutantDetectActual = mutantDetect.isMutant(dnaMutant2lList);
        Assertions.assertTrue(mutantDetectActual);

    }

    @Test
    void isMutant3() {
        boolean mutantDetectActual = mutantDetect.isMutant(dnaMutant3List);
        Assertions.assertTrue(mutantDetectActual);

    }

    @Test
    void isHumanBad() {
        boolean mutantDetectActual = MutantDetect.hasCorrectElements(dnaHumanBadList);
        Assertions.assertFalse(mutantDetectActual);

    }

}