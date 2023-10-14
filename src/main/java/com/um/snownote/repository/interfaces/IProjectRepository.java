package com.um.snownote.repository.interfaces;

import com.um.snownote.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRepository extends MongoRepository<Project, String> {
}
