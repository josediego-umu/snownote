package com.um.snownote.repository.interfaces;

import com.um.snownote.client.HttpUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHttpUrlRepository extends MongoRepository<HttpUrl, String> {

}
