package com.detect.mutant.service.human;

import com.detect.mutant.controller.dto.DNA;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HumanServiceImp implements IHumanService {

    private final IHumanRepository IHumanRepository;
    private final MutantDetect mutantDetect;
    private static final String TIME_ZONE = "America/Bogota";

    @Override
    public Human saveHuman(Human human) {
        HumanData humanData = Mapper.toModel(human);
        return Mapper.toDTO(this.IHumanRepository.save(humanData));
    }

    @Override
    public List<Human> findAllDna() {
        return Mapper.toDTOList(this.IHumanRepository.findAll());
    }

    @Override
    public boolean isMutant(DNA dnaObject) throws DnaBadRequestException{
        String[] dnaArray = dnaObject.getDna();
        String error = GlobalMessage.ERROR_BAD_REQUEST;
        try {
            List<String> dna = Arrays.asList(dnaArray);
            dna = dna.stream()
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
            if (!MutantDetect.hasCorrectElements(dna)) {
                error = GlobalMessage.ERROR_NOT_CORRECT_CHARS;
                throw new Exception();
            } else {
                boolean isMutant = this.mutantDetect.isMutant(dna);
                if (!existDna(dnaObject)) {
                    saveHuman(new Human().toBuilder()
                            .isMutant(isMutant)
                            .dna(dnaArray)
                            .createDate(LocalDateTime.now(ZoneId.of(TIME_ZONE)))
                            .build());
                }

                return isMutant;
            }
        } catch (Exception e) {
            throw new DnaBadRequestException(error);
        }
    }

    private boolean existDna(DNA dna) {
        List<Human> humanList = findAllDna();
        long dnas = humanList.stream()
                .filter(actualHuman -> Arrays.equals(actualHuman.getDna(), dna.getDna()))
                .count();
        if (dnas > 0) {
            return true;
        }
        return false;

    }

}
