package com.um.snownote.controller;


import com.um.snownote.jwtUtils.JwtTokenRequired;
import com.um.snownote.model.Ontology;
import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.IAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/analyzer")
public class AnalyzerController {
    private final IAnalyzer snomedAnalyzer;
    private final IAnalyzer customOntologyAnalyzer;
    private final String DEFAULT_ONTOLOGY = "SNOMED-CT";

    @Autowired
    public AnalyzerController(@Qualifier("AnalyzerSnowedCT") IAnalyzer snomedAnalyzer, @Qualifier("AnalyzerCustomOntology") IAnalyzer customOntologyAnalyzer) {
        this.snomedAnalyzer = snomedAnalyzer;
        this.customOntologyAnalyzer = customOntologyAnalyzer;
    }

    @PostMapping("/project")
    @JwtTokenRequired
    public StructuredData analyzeProject(@RequestHeader("Authorization") String token, @RequestBody StructuredData structuredData, Ontology ontology) throws IOException {

        if (structuredData == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StructuredData is null");

        if (ontology != null && ontology.getName() != null && !ontology.getName().equals(DEFAULT_ONTOLOGY))
            return customOntologyAnalyzer.analyze(structuredData, ontology);


        return snomedAnalyzer.analyze(structuredData, ontology);

    }

    @GetMapping("/labels")
    //@JwtTokenRequired
    public List<String> getLabels(@RequestHeader("Authorization") String token, @RequestParam(name = "value") String value,
                                  @RequestParam(required = false, name = "offset") Integer offset, @RequestParam(required = false, name = "limit") Integer limit, Ontology ontology) {

        if (value == null || value.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "value is null or empty");

        if (offset == null) offset = 1;

        if (limit == null) limit = 10;

        if (ontology != null && ontology.getName() != null && !ontology.getName().equals(DEFAULT_ONTOLOGY))
            return customOntologyAnalyzer.getLabels(value, offset, limit, ontology);


        return snomedAnalyzer.getLabels(value, offset, limit, ontology);

    }

}
