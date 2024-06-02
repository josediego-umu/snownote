package com.um.snownote.services.interfaces;

import com.um.snownote.model.StructuredData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAnalyzer {

    public StructuredData analyze(StructuredData structuredData);
    public List<String> getLabels(String value, int offset, int limit);

}
