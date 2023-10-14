package com.um.snownote.repository.interfaces;

import com.um.snownote.model.StructuredData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStructuredDataRepository extends MongoRepository<StructuredData, String> {

}
