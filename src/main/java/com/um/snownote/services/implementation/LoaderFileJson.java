package com.um.snownote.services.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.ILoaderFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("LoaderFileJson")
public class LoaderFileJson implements ILoaderFile {
    private static final Logger logger = LoggerFactory.getLogger(LoaderFileJson.class);
    ObjectMapper objectMapper;

    public LoaderFileJson() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public StructuredData load(MultipartFile file) {

        try {

            return objectMapper.readValue(file.getInputStream(), StructuredData.class);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;

        }
    }

    @Override
    public StringWriter export(StructuredData structuredData) {
        try {

            StringWriter exportJson = new StringWriter();
            objectMapper.writeValue(exportJson, structuredData);

            return exportJson;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


}
