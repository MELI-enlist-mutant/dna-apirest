package com.detect.mutant.controller;

import com.detect.mutant.controller.dto.DnaSequence;
import com.detect.mutant.controller.handler.exception.DnaBadRequestException;
import com.detect.mutant.controller.handler.message.GlobalMessage;
import com.detect.mutant.service.human.HumanServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT})
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class HumanController {

    private final HumanServiceImp humanServiceImp;

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
        return this.humanServiceImp.isMutant(dna) ? ResponseEntity.ok(GlobalMessage.OK_MUTANT)
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body(GlobalMessage.ERROR_FORBIDDEN);

    }

}
