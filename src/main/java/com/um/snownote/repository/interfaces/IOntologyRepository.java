package com.um.snownote.repository.interfaces;

import com.um.snownote.model.Ontology;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IOntologyRepository extends MongoRepository<Ontology, String>, PagingAndSortingRepository<Ontology, String> {

    public List<Ontology> findByName(String name);

}
