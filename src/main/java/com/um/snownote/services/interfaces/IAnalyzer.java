package com.um.snownote.services.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.um.snownote.model.Label;
import com.um.snownote.model.StructuredData;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO implement here
@Service
public interface IAnalyzer {

    public StructuredData analize(StructuredData structuredData);
    public List<String> getLabels(String value, int offset, int limit);

}
