package com.um.snownote.repository;

import com.um.snownote.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {
    @Query("{'name': ?0}")
    List<User> findUserByName(String name);
}
