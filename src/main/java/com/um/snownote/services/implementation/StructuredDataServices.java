package com.um.snownote.services.implementation;

import com.um.snownote.model.StructuredData;
import com.um.snownote.repository.interfaces.IStructuredDataRepository;
import com.um.snownote.services.interfaces.IStructuredDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO implement here
@Service
public class StructuredDataServices implements IStructuredDataServices {
    @Autowired
    private IStructuredDataRepository structuredDataRepository;

    @Override
    public StructuredData createStructuredData() {
        StructuredData structuredData = new StructuredData();
        structuredDataRepository.save(structuredData);
        return structuredData;
    }

    @Override
    public StructuredData getStructuredData(String id) {
        return structuredDataRepository.findById(id).orElse(null);
    }

    @Override
    public StructuredData updateStructuredData(StructuredData structuredData) {
        return structuredDataRepository.save(structuredData);
    }

    @Override
    public Boolean deleteStructuredData(String id) {
        StructuredData structuredData = getStructuredData(id);
        structuredDataRepository.delete(structuredData);

        return structuredDataRepository.findById(id).isEmpty();
    }
}
