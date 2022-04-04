package com.detect.mutant.controller;

import com.detect.mutant.controller.dto.DNA;
import com.detect.mutant.controller.handler.exception.DnaBadRequestException;
import com.detect.mutant.controller.handler.message.GlobalMessage;
import com.detect.mutant.service.human.HumanServiceImp;
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

    @PostMapping("mutant")
    public ResponseEntity<String> detectMutant(@RequestBody DNA dna) throws DnaBadRequestException {
        return this.humanServiceImp.isMutant(dna) ? ResponseEntity.ok(GlobalMessage.OK_MUTANT) : ResponseEntity.status(HttpStatus.FORBIDDEN).body(GlobalMessage.ERROR_FORBIDDEN);

    }

}
