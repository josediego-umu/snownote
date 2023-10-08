package com.um.snownote.Initializer;

import com.um.snownote.client.HttpClientFactory;
import com.um.snownote.client.HttpUrl;
import com.um.snownote.repository.IHttpUrlRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UrlInitializer {

    private final IHttpUrlRepository iHttpUrlRepository;

    @Autowired
    public UrlInitializer(IHttpUrlRepository iHttpUrlRepository) {
        this.iHttpUrlRepository = iHttpUrlRepository;
    }

    @PostConstruct
    public void init() {
        Map<String, HttpUrl> urls = HttpClientFactory.getUrls();

        List<HttpUrl> listUrls = iHttpUrlRepository.findAll();
        for (HttpUrl url : listUrls) {
            urls.put(url.getName(), url);
        }

    }


}
