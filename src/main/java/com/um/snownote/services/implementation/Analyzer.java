package com.um.snownote.services.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.client.HttpClientFactory;
import com.um.snownote.client.HttpUrl;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Analyzer implements IAnalyzer {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Analyzer.class);

    @Override
    public StructuredData analyze(StructuredData structuredData) {

        List<List<String>> rows = structuredData.getRows();
        Map<String, String> labelMap = new HashMap<>();

        for (List<String> row : rows) {

            for (String value : row) {

                if (labelMap.get(value) == null || labelMap.get(value).isEmpty()) {
                    List<String> labels = getLabels(value, 0, 1);
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

    public List<String> getLabels(String value, int offset, int limit) {

        List<String> labels;

        if (value.isEmpty() || value.isBlank() || isBoolean(value) || isNumber(value))
            return new ArrayList<>();

        value = value.replaceAll("[|,;/-]", " ");

        if (value.length() < 3) {
            value = value + "   ";
        }

        labels = requestLabel(value, offset, limit);

        return labels;

    }

    private boolean isNumber(String value) {
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

    private List<String> requestLabel(String value, int offset, int limit) {

        try {

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
                List<String> labels = new ArrayList<>();

                if (itemsArray.isArray()) {
                    for (JsonNode item : itemsArray) {

                        labels.add(item.get("idAndFsnTerm").toString());
                    }
                }


                return labels;
            }

            return new ArrayList<>();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>() {
            };
        }

    }
}
