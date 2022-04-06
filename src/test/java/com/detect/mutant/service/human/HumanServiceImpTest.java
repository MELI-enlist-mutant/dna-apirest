package com.detect.mutant.service.human;

import com.detect.mutant.controller.dto.DnaSequence;
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
    private DnaSequence dnaMutant;
    private DnaSequence dnaHuman;
    private DnaSequence dnaHumanBad2;
    private List<HumanData> humanList;

    @BeforeEach
    void setUp() {
        humanServiceImp = new HumanServiceImp(iHumanRepository, mutantDetect);
        dnaMutant = DnaSequence.builder()
                .dna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"})
                .build();
        dnaHuman = DnaSequence.builder()
                .dna(new String[]{"GTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCACTA", "TCACTG"})
                .build();
        dnaHumanBad2 = DnaSequence.builder()
                .dna(new String[]{"TTGCGA", "CAGTCCC", "TTATGT", "AGAAGG", "CCCTTA", "TCACTG"})
                .build();
        mutantExpected = HumanData.builder()
                .dnaSequence(dnaMutant.getDna())
                .isMutant(true)
                .createDate(LocalDateTime.now())
                .build();
        humanExpected = HumanData.builder()
                .dnaSequence(dnaHuman.getDna())
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
        Assertions.assertEquals(GlobalMessage.ERROR_NOT_CORRECT_CHARS, e.getMessage());

    }

}