package com.um.snownote.controller;


import com.um.snownote.jwtUtils.JwtTokenRequired;
import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.IAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/analyzer")
public class AnalyzerController {
    private final IAnalyzer analyzer;

    @Autowired
    public AnalyzerController(IAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @PostMapping("/project")
    @JwtTokenRequired
    public StructuredData analyzeProject(@RequestHeader("Authorization") String token, @RequestBody StructuredData structuredData) throws IOException {

        if (structuredData == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StructuredData is null");

        return analyzer.analyze(structuredData, null);

    }

    @GetMapping("/labels")
    @JwtTokenRequired
    public List<String> getLabels(@RequestHeader("Authorization") String token,@RequestParam(name = "value") String value, @RequestParam(required = false, name = "offset") Integer offset, @RequestParam(required = false, name = "limit") Integer limit) {

        if (value == null || value.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "value is null or empty");

        if (offset == null) offset = 0;

        if (limit == null) limit = 10;

        return analyzer.getLabels(value, offset, limit, null);

    }

}
