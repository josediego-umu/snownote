package com.um.snownote.services.implementation;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.ILoaderFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service("LoaderFileCsv")
public class LoaderFileCsv implements ILoaderFile {

    private static final Logger logger = LoggerFactory.getLogger(LoaderFileCsv.class);

    @Override
    public StructuredData load(MultipartFile file) {

        try {
            CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())));
            ;

            return load(csvReader);

        } catch (IOException | CsvValidationException ioe) {
            logger.error(ioe.getMessage(), ioe);
            return null;

        }

    }

    @Override
    public StringWriter export(StructuredData structuredData) {

        try {

            StringWriter exportCSV = new StringWriter();

            if (structuredData == null)
                return exportCSV;

            CSVWriter csvWriter = new CSVWriter(exportCSV);
            List<List<String>> rows = structuredData.getRows();
            Map<String, String> label = structuredData.getLabels();

            for (List<String> row : rows) {

                String[] csvRow = (String[]) row.stream()
                        .map(value -> label.get(value) != null ? value + label.get(value) : value)
                        .toArray();

                csvWriter.writeNext(csvRow);

            }

            csvWriter.close();

            return exportCSV;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }


    private StructuredData load(CSVReader csvReader) throws IOException, CsvValidationException {

        StructuredData structuredData = new StructuredData();
        Map<String, Set<String>> columnsToValues = new HashMap<>();

        String[] line;
        while ((line = csvReader.readNext()) != null) {

            structuredData.getRows().add(List.of(line));

        }

        List<List<String>> rows = structuredData.getRows();

        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                String value = row.get(j);
                if (!columnsToValues.containsKey(rows.get(0).get(j))) {
                    columnsToValues.put(rows.get(0).get(j), new HashSet<>());
                }
                columnsToValues.get(rows.get(0).get(j)).add(value);
            }

        }

        structuredData.setColumnsToValues(columnsToValues);

        return structuredData;
    }


}
