package com.um.snownote.client;


import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class HttpClientFactory {

    private static Map<String, HttpUrl> httpUrlMap;
    private static HttpClient client;

    public static HttpClient createHttpClient() {

        if (client == null) {
            client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();
        }

        return client;
    }

    public static Map<String, HttpUrl> getUrls() {

        if (httpUrlMap == null) {
            httpUrlMap = new HashMap<>();
        }

        return httpUrlMap;
    }


}
