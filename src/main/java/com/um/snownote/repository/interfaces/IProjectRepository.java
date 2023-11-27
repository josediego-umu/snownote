package com.um.snownote.repository.interfaces;

import com.um.snownote.model.Project;
import com.um.snownote.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IProjectRepository extends MongoRepository<Project, String> {
    @Query("{'name': ?0, 'status': true}")
    List<Project> findByName(String name);

    @Query("{'owner': ?0, 'status': true}")
    List<Project> findByOwner(User owner);

    @Query("{'writers': ?0, 'status': true}")
    List<Project> findByWriters(User writer);

    @Query("{'readers': ?0, 'status': true}")
    List<Project> findProjectsByReaders(User reader);

    @Query("{'createDate': {$gte: ?0, $lte: ?1}, 'status': true}")
    List<Project> findByCreateDateBetween(Date startDate, Date endDate);

    @Query("{'updateDate': {$gte: ?0, $lte: ?1}, 'status': true}")
    List<Project> findByUpdateDateBetween(Date startDate, Date endDate);
}
