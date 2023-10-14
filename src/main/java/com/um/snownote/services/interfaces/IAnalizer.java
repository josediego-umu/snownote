package com.um.snownote.services.interfaces;

import com.um.snownote.model.StructuredData;
import org.springframework.stereotype.Service;

@Service
public interface IAnalizer {

    public StructuredData analize(StructuredData structuredData);

}
