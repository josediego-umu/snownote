package com.um.snownote.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.Jsr310CodecProvider;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;
import java.util.Set;

import static org.bson.codecs.configuration.CodecRegistries.*;

@Configuration
@EnableMongoRepositories(basePackages = "com.um.snownote.repository")
@ComponentScan(basePackages = "com.um.snownote")
public class MongoConfig {
    @Bean
    public MongoClient mongo() {


        ConnectionString connectionString = new ConnectionString("mongodb+srv://snownote:snownote@snownote.ua4gsnu.mongodb.net/?retryWrites=true&w=majority&appName=SnowNote");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), "SnowNote");
        Set<String> colleSet = mongoTemplate.getCollectionNames();

        if (!colleSet.contains("user")) {
            mongoTemplate.createCollection("user");
        }

        if (!colleSet.contains("httpUrls")) {
            mongoTemplate.createCollection("httpUrls");
        }

        if (!colleSet.contains("structuredDatas")) {
            mongoTemplate.createCollection("structuredDatas");
        }

        if (!colleSet.contains("projects")) {
            mongoTemplate.createCollection("projects");
        }


        return mongoTemplate;
    }


}
