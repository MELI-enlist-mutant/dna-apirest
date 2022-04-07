package com.detect.mutant.controller;

import com.detect.mutant.controller.dto.DnaSequence;
import com.detect.mutant.controller.dto.ResponseObject;
import com.detect.mutant.controller.handler.exception.DnaBadRequestException;
import com.detect.mutant.controller.handler.message.GlobalMessage;
import com.detect.mutant.service.human.HumanServiceImp;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "dna-apirest", description = "Este api permite identificar si un humano es " +
        "mutante o no, a partir de la secuencia de ADN. El api recibe la secuencia de adn y devuelve un " +
        "mensaje \"Humano!!! Fuera de acá\" en caso de que la secuencia sea de un humano normal y " +
        "devuelve \"Mutante!!! Bienvenido a Magneto Club\" cuando se identifica que es un humano mutante. " +
        "Si se hace el envío de una secuencia de adn incorrecta o con mal formato se devuelve el " +
        "mensaje \"Formato del ADN incorrecto\". Adicionalmente, permite consultar las estadísticas de mutamtes " +
        "y humanos guardados en la base de datos.", version = "v1.0",
        license = @License(name = "De uso libre, desarrollado por Daniel Salazar",
                url = "https://github.com/MELI-enlist-mutant/dna-apirest/tree/master")))

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT})
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class HumanController {

    private final HumanServiceImp humanServiceImp;
    private static final Gson gson = new Gson();


    @Operation(summary = "Mutante o humano", description = "Recibe una secuencia de ADN y responde con un mensaje, ya " +
            "sea mutante (200) o humano (403)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = GlobalMessage.ERROR_FORBIDDEN,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "200", description = GlobalMessage.OK_MUTANT,
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("mutant")
    public ResponseEntity<String> detectMutant(@RequestBody DnaSequence dna) throws DnaBadRequestException {
        return this.humanServiceImp.isMutant(dna) ? ResponseEntity.ok(gson.toJson(ResponseObject.builder().message(GlobalMessage.OK_MUTANT).build()))
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body(gson.toJson(ResponseObject.builder().message(GlobalMessage.ERROR_FORBIDDEN).build()));

    }

}
