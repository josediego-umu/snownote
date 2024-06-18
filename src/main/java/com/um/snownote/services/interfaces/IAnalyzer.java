package com.um.snownote.services.interfaces;

import com.um.snownote.model.LabelSummary;
import com.um.snownote.model.Ontology;
import com.um.snownote.model.StructuredData;
import org.springframework.stereotype.Service;

@Service
public interface IAnalyzer {

    public StructuredData analyze(StructuredData structuredData, Ontology ontology);
    public LabelSummary getLabels(String value, int offset, int limit, Ontology ontology);

}
