package com.detect.mutant.service.human;

import com.detect.mutant.controller.dto.DnaSequence;
import com.detect.mutant.controller.dto.Human;
import com.detect.mutant.controller.handler.exception.DnaBadRequestException;
import com.detect.mutant.controller.handler.mapper.Mapper;
import com.detect.mutant.controller.handler.message.GlobalMessage;
import com.detect.mutant.model.HumanData;
import com.detect.mutant.repository.IHumanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class HumanServiceImp implements IHumanService {

    private final IHumanRepository humanRepository;
    private final MutantDetect mutantDetect;
    private static final String TIME_ZONE = "America/Bogota";

    @Override
    public Human saveHuman(Human human) {
        HumanData humanData = Mapper.toModel(human);
        return Mapper.toDTO(this.humanRepository.save(humanData));
    }

    @Override
    public List<Human> findAllDna() {
        return Mapper.toDTOList(this.humanRepository.findAll());
    }

    @Override
    public boolean isMutant(DnaSequence dnaObject) throws DnaBadRequestException {
        String[] dnaArray = dnaObject.getDna();
        String error = GlobalMessage.ERROR_BAD_REQUEST;
        try {
            List<String> dna = Arrays.asList(dnaArray);
            dna = dna.stream()
                    .map(String::toUpperCase)
                    .toList();
            if (!MutantDetect.hasCorrectElements(dna)) {
                error = GlobalMessage.ERROR_NOT_CORRECT_CHARS;
                throw new DnaBadRequestException(GlobalMessage.ERROR_NOT_CORRECT_CHARS);
            }
                boolean isMutant = this.mutantDetect.isMutant(dna);
                if (!existDna(dnaObject)) {
                    saveHuman(new Human().toBuilder()
                            .isMutant(isMutant)
                            .dna(dnaArray)
                            .createDate(LocalDateTime.now(ZoneId.of(TIME_ZONE)))
                            .build());
                }

                return isMutant;

        } catch (Exception e) {
            throw new DnaBadRequestException(error);
        }
    }

    private boolean existDna(DnaSequence dna) {
        List<Human> humanList = findAllDna();
        long dnas = humanList.stream()
                .filter(actualHuman -> Arrays.equals(actualHuman.getDna(), dna.getDna()))
                .count();
        return dnas > 0;

    }

}
