package com.um.snownote.services.implementation;

import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.IAnalizer;
import org.springframework.stereotype.Service;

@Service
public class Analyzer implements IAnalizer {
    @Override
    public StructuredData analize(StructuredData structuredData) {
        return null;
    }
}
