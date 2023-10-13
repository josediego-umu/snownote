package com.um.snownote.services.implementation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.um.snownote.model.Row;
import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.LoaderFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class LoaderFileCsv implements LoaderFile {

    private static final Logger logger = LoggerFactory.getLogger(LoaderFileCsv.class);

    @Override
    public StructuredData load(String path) {

        try {
            Path pathFile = Paths.get(path);
            return load(pathFile);

        } catch (IOException | CsvValidationException ioe) {
            logger.error(ioe.getMessage(), ioe);
            return null;

        }

    }

    private StructuredData load(Path pathFile) throws IOException, CsvValidationException {

        StructuredData structuredData = new StructuredData();

        try (Reader reader = Files.newBufferedReader(pathFile)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    structuredData.getRows().add(new Row(List.of(line)));
                }
            }
        }
        return structuredData;
    }


}
