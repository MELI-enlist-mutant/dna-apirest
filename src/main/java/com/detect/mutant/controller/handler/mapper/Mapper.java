package com.detect.mutant.controller.handler.mapper;

import com.detect.mutant.controller.dto.Human;
import com.detect.mutant.model.HumanData;

import java.util.List;

public class Mapper {

    private Mapper() {
    }

    public static Human toDTO(HumanData humanData) {
        return Human.builder()
                .dna(humanData.getDnaSequence())
                .isMutant(humanData.isMutant())
                .createDate(humanData.getCreateDate())
                .build();
    }

    public static HumanData toModel(Human human) {
        return HumanData.builder()
                .dnaSequence(human.getDna())
                .isMutant(human.isMutant())
                .createDate(human.getCreateDate())
                .build();
    }

    public static List<Human> toDTOList(List<HumanData> humanDataList) {
        return humanDataList.stream()
                .map(s -> Human.builder()
                        .dna(s.getDnaSequence())
                        .isMutant(s.isMutant())
                        .createDate(s.getCreateDate())
                        .build())
                .toList();
    }
}
