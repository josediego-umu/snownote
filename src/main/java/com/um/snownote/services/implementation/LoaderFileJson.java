package com.um.snownote.services.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.LoaderFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class LoaderFileJson implements LoaderFile {
    private static final Logger logger = LoggerFactory.getLogger(LoaderFileJson.class);
    ObjectMapper objectMapper;

    public LoaderFileJson() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public StructuredData load(String path) {
        try {
            Path pathFile = Paths.get(path);
            return load(pathFile);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;

        }
    }

    @Override
    public void export(StructuredData structuredData, String path) {
        try {
            objectMapper.writeValue(new FileWriter(path), structuredData);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private StructuredData load(Path pathFile) throws IOException {

        File file = pathFile.toFile();
        StructuredData structuredData = objectMapper.readValue(file, StructuredData.class);
        return structuredData;
    }

}
