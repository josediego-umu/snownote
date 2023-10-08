package com.um.snownote.repository;

import com.um.snownote.client.HttpUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IHttpUrlRepository extends MongoRepository<HttpUrl, String> {

}
