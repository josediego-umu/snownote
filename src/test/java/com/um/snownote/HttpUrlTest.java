package com.um.snownote;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.client.HttpClientFactory;
import com.um.snownote.client.HttpUrl;
import com.um.snownote.repository.IHttpUrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@SpringBootTest
public class HttpUrlTest {

    @Autowired
    private IHttpUrlRepository IHttpUrlRepository;

    @Test
    public void insertHttpUrl(){

        HttpUrl httpUrl = new HttpUrl();
        httpUrl.setName("branches");
        httpUrl.setUrl("http://localhost:8080/branches");
        httpUrl.setMethod("GET");

        IHttpUrlRepository.save(httpUrl);

    }
    @Test
    public void getMapUrls(){
       Map<String, HttpUrl> urls =  HttpClientFactory.getUrls();

       Assertions.assertNotNull(urls);
       Assertions.assertFalse(urls.isEmpty());
    }
    @Test
    public void clientTest() throws Exception{
        HttpClient client =  HttpClientFactory.createHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpClientFactory.getUrls().get("branches").getUrl()))
                .GET()
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.body());
        Assertions.assertFalse(response.body().toString().isEmpty());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.body().toString());
        Assertions.assertNotNull(jsonNode);
        Assertions.assertFalse(jsonNode.isEmpty());
    }

}
