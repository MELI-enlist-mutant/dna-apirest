package com.detect.mutant.controller;

import com.detect.mutant.controller.dto.Statistics;
import com.detect.mutant.service.stats.StatsServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT})
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class StatsController {

    private final StatsServiceImp statsServiceImp;

    @Operation(summary = "Estadisticas de mutantes y humanos", description = "Responde con un objeto de estadísticas de" +
            "cantidad de humanos, catidad de mutantes y el ratio de los mutantes" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Statistics.class))})
    })
    @GetMapping("stats")
    public Statistics getStats() {
        return this.statsServiceImp.getStats();
    }
}