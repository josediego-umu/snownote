package com.um.snownote.controller;

import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.IAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public StructuredData analyzeProject(StructuredData structuredData) {

        if (structuredData == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StructuredData is null");

        return analyzer.analyze(structuredData);

    }

    @GetMapping("/labels")
    public List<String> getLabels(String value, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {

        if (value == null || value.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "value is null or empty");

        if (offset == null) offset = 0;

        if (limit == null) limit = 10;

        return analyzer.getLabels(value, offset, limit);

    }

}
