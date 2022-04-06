package com.detect.mutant.service.human;

import com.detect.mutant.controller.dto.DnaSequence;
import com.detect.mutant.controller.dto.Human;
import com.detect.mutant.controller.handler.exception.DnaBadRequestException;

import java.util.List;

public interface IHumanService {
    Human saveHuman(Human human);
    List<Human> findAllDna();
    boolean isMutant(DnaSequence dnaObject) throws DnaBadRequestException;
}
