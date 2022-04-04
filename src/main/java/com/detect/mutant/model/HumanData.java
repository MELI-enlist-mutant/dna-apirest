package com.detect.mutant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document("human")
public class HumanData {

    private static final String ID="id";
    private static final String DNA="dna";
    private static final String IS_MUTANT="is_mutant";
    private static final String CREATE_DATE="create_date";


    @Id
    @Field(ID)
    private String idHuman;

    @Field(DNA)
    @Indexed(unique=true)
    private String[] dna;

    @Field(IS_MUTANT)
    private boolean isMutant;

    @Field(CREATE_DATE)
    private LocalDateTime createDate;

}
