package com.um.snownote;

import com.um.snownote.model.StructuredData;
import com.um.snownote.services.implementation.LoaderFileCsv;
import com.um.snownote.services.interfaces.IAnalyzer;
import com.um.snownote.services.interfaces.ILoaderFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AnalyzerTest {/*
    @Autowired
    private IAnalyzer analyzer;
    private final String FILEPATHCSV = "C:\\UM\\TFG\\examples\\patient_hospital_data-RESQformat.csv";
    private final String FILEPATHOUTCSV = "C:\\UM\\TFG\\examples\\TEST_ANALYZER_OUT.csv";
    private final ILoaderFile loaderFileCsv = new LoaderFileCsv();

    @Test
    public void testGetLabel() {
        List<String> labels = analyzer.getLabels("Female", 0, 5);
        Assertions.assertNotNull(labels);
        System.out.println(labels);
    }

    @Test
    public void testAnalizeStructuredData() {

        StructuredData structuredDataLoad = loaderFileCsv.load(FILEPATHCSV);

        StructuredData structuredDataAnalyzed = analyzer.analyze(structuredDataLoad);

        //loaderFileCsv.export(structuredDataAnalyzed, FILEPATHOUTCSV);

    }
*/
}
