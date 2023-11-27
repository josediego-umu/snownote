package com.um.snownote;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.client.HttpClientFactory;
import com.um.snownote.client.HttpUrl;
import com.um.snownote.model.Label;
import com.um.snownote.model.StructuredData;
import com.um.snownote.repository.interfaces.IHttpUrlRepository;
import com.um.snownote.services.implementation.Analyzer;
import com.um.snownote.services.implementation.LoaderFileCsv;
import com.um.snownote.services.interfaces.IAnalyzer;
import com.um.snownote.services.interfaces.LoaderFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class AnalyzerTest {
    @Autowired
    private IAnalyzer analyzer;
    private final String FILEPATHCSV = "C:\\UM\\TFG\\examples\\patient_hospital_data-RESQformat.csv";
    private final String FILEPATHOUTCSV = "C:\\UM\\TFG\\examples\\TEST_ANALYZER_OUT.csv";
    private final LoaderFile loaderFileCsv = new LoaderFileCsv();

    @Test
    public void testGetLabel() {
        List<String> labels = analyzer.getLabels("Female", 0, 5);
        Assertions.assertNotNull(labels);
        System.out.println(labels);
    }

    @Test
    public void testAnalizeStructuredData() {

        StructuredData structuredDataLoad = loaderFileCsv.load(FILEPATHCSV);

        StructuredData structuredDataAnalyzed = analyzer.analize(structuredDataLoad);

        loaderFileCsv.export(structuredDataAnalyzed, FILEPATHOUTCSV);

    }

}
