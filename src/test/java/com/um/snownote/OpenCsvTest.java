package com.um.snownote;

import com.opencsv.CSVReader;
import com.um.snownote.model.Project;
import com.um.snownote.model.Row;
import com.um.snownote.model.StructuredData;
import com.um.snownote.model.User;
import com.um.snownote.services.implementation.LoaderFileCsv;
import com.um.snownote.services.implementation.LoaderFileJson;
import com.um.snownote.services.interfaces.ILoaderFile;
import com.um.snownote.services.interfaces.IProjectServices;
import com.um.snownote.services.interfaces.IStructuredDataServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OpenCsvTest {
/*
    @Autowired
    private IProjectServices projectServices;
    @Autowired
    private IStructuredDataServices structuredDataServices;
    private final String FILEPATHCSV = "C:\\UM\\TFG\\examples\\patient_hospital_data-RESQformat.csv";
    private final String FILEPATHOUTCSV = "C:\\UM\\TFG\\examples\\TEST_OUT.csv";
    private final String FILEPATHOUTJSON = "C:\\UM\\TFG\\examples\\TEST_OUT.json";
    private final String FILEPATHJSON = "C:\\UM\\TFG\\examples\\patient_hospital_data-RESQformat.json";
    private final ILoaderFile loaderFileCsv = new LoaderFileCsv();
    private final ILoaderFile loaderFileJson = new LoaderFileJson();


    private List<String[]> readerFileCsv(Path filePath) throws Exception {
        List<String[]> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            }
        }

        return list;
    }

    @Test
    public void readerFileCsvTest() throws Exception {

        Path path = Paths.get(FILEPATHCSV);
        List<String[]> csvFile = readerFileCsv(path);

        for (String[] line : csvFile) {

            for (String s : line) {
                System.out.print(s + ',');
            }
            System.out.println();

        }

    }

    @Test
    public void loaderFileCsvTest() {

        StructuredData structuredData = loaderFileCsv.load(FILEPATHCSV);
        for (Row row : structuredData.getRows()) {

            for (String s : row.getValuesRow()) {
                System.out.print(s + ',');
            }

            System.out.println();
        }
    }

    @Test
    public void loaderFileJsonTest() {

        StructuredData structuredData = loaderFileJson.load(FILEPATHJSON);
        for (Row row : structuredData.getRows()) {

            for (String s : row.getValuesRow()) {
                System.out.print(s + ",");
            }

            System.out.println();
        }

    }

    @Test
    public void exportFileCsvTest() {

        StructuredData structuredData = loaderFileCsv.load(FILEPATHCSV);
       // loaderFileCsv.export(structuredData, FILEPATHOUTCSV);

    }

    @Test
    public void exportFileJsonTest() {

        StructuredData structuredData = loaderFileJson.load(FILEPATHJSON);
        //loaderFileJson.export(structuredData, FILEPATHOUTJSON);

    }
    */
}
