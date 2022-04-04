package com.detect.mutant.service.human;

import com.detect.mutant.controller.dto.DNA;
import com.detect.mutant.controller.handler.exception.DnaBadRequestException;
import com.detect.mutant.controller.handler.message.GlobalMessage;
import com.detect.mutant.model.HumanData;
import com.detect.mutant.repository.IHumanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class HumanServiceImpTest {

    private final IHumanRepository iHumanRepository = Mockito.mock(IHumanRepository.class);
    private final MutantDetect mutantDetect = Mockito.mock(MutantDetect.class);

    private HumanServiceImp humanServiceImp;
    private HumanData mutantExpected;
    private HumanData humanExpected;
    private DNA dnaMutant;
    private DNA dnaHuman;
    private DNA dnaHumanBad2;
    private List<HumanData> humanList;

    @BeforeEach
    void setUp() {
        humanServiceImp = new HumanServiceImp(iHumanRepository, mutantDetect);
        dnaMutant = DNA.builder()
                .dna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"})
                .build();
        dnaHuman = DNA.builder()
                .dna(new String[]{"GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCACTA", "TCACTG"})
                .build();
        dnaHumanBad2 = DNA.builder()
                .dna(new String[]{"TTGCGA", "CAGTCCC", "TTATGT", "AGAAGG", "CCCTTA", "TCACTG"})
                .build();
        mutantExpected = HumanData.builder()
                .dna(dnaMutant.getDna())
                .isMutant(true)
                .createDate(LocalDateTime.now())
                .build();
        humanExpected = HumanData.builder()
                .dna(dnaHuman.getDna())
                .isMutant(false)
                .createDate(LocalDateTime.now())
                .build();


        humanList = new ArrayList<>();
        humanList.add(mutantExpected);
        humanList.add(humanExpected);

    }

    @Test
    void isHuman() throws Exception{
        when(iHumanRepository.findAll()).thenReturn(humanList);
        when(mutantDetect.isMutant(anyList())).thenReturn(false);
        when(iHumanRepository.save(any())).thenReturn(humanExpected);

        boolean isMutantActual = humanServiceImp.isMutant(dnaHuman);
        Assertions.assertFalse(isMutantActual);

    }
    @Test
    void isMutanOk() throws Exception{

        when(mutantDetect.isMutant(anyList())).thenReturn(true);
        when(iHumanRepository.save(any())).thenReturn(mutantExpected);

        boolean isMutantActual = humanServiceImp.isMutant(dnaMutant);
        Assertions.assertTrue(isMutantActual);
    }

    @Test
    void invalidFormat() throws Exception{
            when(mutantDetect.isMutant(new ArrayList<>())).thenThrow(new DnaBadRequestException(GlobalMessage.ERROR_NOT_CORRECT_CHARS));
        when(iHumanRepository.save(any())).thenReturn(humanExpected);

        Exception e =
                Assertions.assertThrows(DnaBadRequestException.class, () -> {
                    humanServiceImp.isMutant(dnaHumanBad2);
                });
        Assertions.assertEquals(e.getMessage(), GlobalMessage.ERROR_NOT_CORRECT_CHARS);

    }

}