package com.detect.mutant.controller;

import com.detect.mutant.controller.dto.DnaSequence;
import com.detect.mutant.controller.dto.ResponseObject;
import com.detect.mutant.controller.handler.exception.DnaBadRequestException;
import com.detect.mutant.controller.handler.message.GlobalMessage;
import com.detect.mutant.service.human.HumanServiceImp;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

class HumanControllerTest {

    private HumanController humanController;
    private final HumanServiceImp humanServiceImp = Mockito.mock(HumanServiceImp.class);
    private static final Gson gson = new Gson();
    private DnaSequence dnaMutant;
    private DnaSequence dnaHuman;
    private DnaSequence dnaHumanBad;
    private ResponseEntity<String> responseEntityMutantExpected;
    private ResponseEntity<String> responseEntityHumanExpected;



    @BeforeEach
    void setUp() {
        humanController = new HumanController(humanServiceImp);
        dnaMutant = DnaSequence.builder()
                .dna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"})
                .build();
        dnaHuman = DnaSequence.builder()
                .dna(new String[]{"TTGCGA","CAGTGC","TTATGT","AGAAAG","CTCCTA","TCACTG"})
                .build();
        dnaHumanBad = DnaSequence.builder()
                .dna(new String[]{"TTGCGAA","CAGTGC","TTATGT","AGAAAG","CTCCTA","TCACTG"})
                .build();

        responseEntityMutantExpected=ResponseEntity.ok(gson.toJson(ResponseObject.builder().message(GlobalMessage.OK_MUTANT).build()));
        responseEntityHumanExpected=ResponseEntity.status(HttpStatus.FORBIDDEN).body(gson.toJson(ResponseObject.builder().message(GlobalMessage.ERROR_FORBIDDEN).build()));

    }


    @Test
    void isMutantTest() {
        when(humanServiceImp.isMutant(dnaMutant)).thenReturn(true);
        ResponseEntity<String> responseEntityActual = humanController.detectMutant(dnaMutant);
        Assertions.assertEquals(responseEntityActual, responseEntityMutantExpected);
    }

    @Test
    void isHumanTest() {
        when(humanServiceImp.isMutant(dnaHuman)).thenReturn(false);
        ResponseEntity<String> responseEntityActual = humanController.detectMutant(dnaHuman);
        Assertions.assertEquals(responseEntityActual, responseEntityHumanExpected);
    }

    @Test
    void isHuman1Test() throws Exception{

        when(humanServiceImp.isMutant(dnaHumanBad)).thenThrow(new DnaBadRequestException(GlobalMessage.ERROR_NOT_CORRECT_CHARS));

        Exception e =
                Assertions.assertThrows(DnaBadRequestException.class, () -> {
                    humanServiceImp.isMutant(dnaHumanBad);
                });
        Assertions.assertEquals(GlobalMessage.ERROR_NOT_CORRECT_CHARS, e.getMessage());
    }

}