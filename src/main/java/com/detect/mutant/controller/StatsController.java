package com.detect.mutant.controller;

import com.detect.mutant.controller.dto.Statistics;
import com.detect.mutant.service.stats.StatsServiceImp;
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

    @GetMapping("stats")
    public Statistics getStats() {
        return this.statsServiceImp.getStats();
    }
}