package com.um.snownote.repository.interfaces;

import com.um.snownote.model.Project;
import com.um.snownote.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IProjectRepository extends MongoRepository<Project, String>, PagingAndSortingRepository<Project, String> {

    List<Project> findByName(String name, Pageable pageable);
    List<Project> findAllByOwner(User owner, Pageable pageable);
    List<Project> findByWriters(User writer);
    List<Project> findProjectsByReaders(User reader);
    List<Project> findProjectsByReadersOrWriters(User userR, User userW);
    List<Project> findByCreateDateBetween(Date startDate, Date endDate);
    List<Project> findByUpdateDateBetween(Date startDate, Date endDate);
    @Query(value = "db.projects.find({}).skip(?0).limit(?1)", count = true)
    List<Project> findAllPageable(Pageable pageable);

}
