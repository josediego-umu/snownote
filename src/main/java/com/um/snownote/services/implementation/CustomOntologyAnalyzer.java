package com.um.snownote.services.implementation;

import com.um.snownote.cache.OntologyCache;
import com.um.snownote.model.Ontology;
import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.IAnalyzer;
import com.um.snownote.services.interfaces.IOntologyService;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("AnalyzerCustomOntology")
public class CustomOntologyAnalyzer implements IAnalyzer {
    private final IOntologyService ontologyService;
    private final OntologyCache ontologyCache;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomOntologyAnalyzer.class);


    @Autowired
    public CustomOntologyAnalyzer(IOntologyService ontologyService) {
        this.ontologyService = ontologyService;
        this.ontologyCache = OntologyCache.getInstance();
    }

    @Override
    public StructuredData analyze(StructuredData structuredData, Ontology ontology) {

        if (structuredData == null)
            return null;

        List<List<String>> rows = structuredData.getRows();

        if (rows == null)
            return null;

        for (List<String> row : rows) {
            for (String value : row) {
                if (value != null && !value.isEmpty()) {
                    List<String> labels = getLabels(value, 0, 1, null);
                    if (!labels.isEmpty()) {
                        String label = labels.get(0);
                        structuredData.getLabels().put(value, label);
                    }
                }
            }
        }

        return null;
    }

    @Override
    public List<String> getLabels(String value, int offset, int limit, Ontology ontology) {

        if (value == null || value.isEmpty())
            return null;


        OWLOntology owlOntology = getOWLOntology(ontology);

        if (owlOntology == null)
            return null;


        List<String> labels = owlOntology.getClassesInSignature().stream()
                .filter(owlClass -> {
                    System.out.println(owlClass.getIRI().getFragment().toUpperCase());
                    return owlClass.getIRI().getFragment().toUpperCase().contains(value.toUpperCase());

                })
                .skip((long) Math.max(0, (offset - 1)) * limit)
                .limit(limit)
                .map(owlClass -> owlClass.getIRI().getFragment())
                .toList();

        return labels;


    }


    private OWLOntology getOWLOntology(Ontology ontology) {

        OWLOntology owlOntology = (OWLOntology) ontologyCache.getObject(ontology.getId());

        if (owlOntology != null)
            return owlOntology;


        owlOntology = ontologyService.getOntologyById(ontology.getId());

        ontologyCache.addObject(ontology.getId(), owlOntology);

        return owlOntology;


    }


}
