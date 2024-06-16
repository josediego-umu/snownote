package com.um.snownote.services.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.client.HttpClientFactory;
import com.um.snownote.client.HttpUrl;
import com.um.snownote.model.Ontology;
import com.um.snownote.model.StructuredData;
import com.um.snownote.services.interfaces.IAnalyzer;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service("AnalyzerSnowedCT")
public class SnowedCTAnalyzer implements IAnalyzer {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SnowedCTAnalyzer.class);

    @Override
    public StructuredData analyze(StructuredData structuredData, Ontology ontology) {

        List<List<String>> rows = structuredData.getRows();
        Map<String, String> labelMap = new HashMap<>();

        structuredData.setColumnsToValues(columnsToValueInit(rows.get(0)));

        for (int i = 1; i < rows.size(); i++) {

            for (int j = 0; j < rows.get(i).size(); j++) {

                String value = rows.get(i).get(j);

                if (!value.isEmpty() && !isNumber(value)) {

                    structuredData.getColumnsToValues().get(structuredData.getRows().get(0).get(j)).add(value);
                }

                if (!value.isEmpty() && labelMap.get(value) == null) {

                    List<String> labels = getLabels(value, 0, 1, null);
                    if (!labels.isEmpty()) {
                        String label = labels.get(0);
                        labelMap.put(value, label);
                    }
                }
            }

        }

        structuredData.setLabels(labelMap);
        return structuredData;
    }

    public List<String> getLabels(String value, int offset, int limit, Ontology ontology) {

        List<String> labels;

        if (value.isEmpty() || value.isBlank() || isBoolean(value) || isNumber(value)
                || isDate(value) || haveDot(value))
            return new ArrayList<>();

        value = value.replaceAll("[|,;/-]", " ");

        if (value.length() < 3) {
            value = value + "   ";
        }

        labels = requestLabel(value, offset, limit);

        return labels;

    }

    private boolean isNumber(String value) {
        return isFloat(value) || isDouble(value);
    }

    private boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    private boolean isDate(String value) {
        return value.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private boolean haveDot(String value) {
        return value.contains(".") || value.contains(",") || value.contains(";");
    }

    private List<String> requestLabel(String value, int offset, int limit) {

        try {

            List<String> labels = new ArrayList<>();

            HttpClient client = HttpClientFactory.createHttpClient();
            HttpUrl url = HttpClientFactory.getUrls().get("concepts");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url.getUrl() + ("?term=" + URLEncoder.encode(value, StandardCharsets.UTF_8) + "&limit=" + limit +
                            "&offset=" + offset + "&activeFilter=true" + "&termActive=true")))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            if (statusCode == 200) {
                ObjectMapper Mapper = new ObjectMapper();

                JsonNode responseJson = Mapper.readTree(response.body());
                JsonNode itemsArray = responseJson.get("items");


                if (itemsArray.isArray()) {
                    for (JsonNode item : itemsArray) {

                        labels.add(item.get("idAndFsnTerm").toString());
                    }
                }

            }

            if (labels.isEmpty())
                labels.add("");

            return labels;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>() {
            };
        }

    }

    private HashMap<String, Set<String>> columnsToValueInit(List<String> header) {
        HashMap<String, Set<String>> columnsToValues = new HashMap<>();

        for (String column : header) {
            columnsToValues.put(column, new HashSet<>());
        }

        return columnsToValues;
    }

}