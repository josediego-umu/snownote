package com.um.snownote.repository.interfaces;

import com.um.snownote.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {
    @Query("{'name': ?0}")
    List<User> findUserByName(String name);

    @Query("{'username': ?0, 'password': ?1}")
    User findUserByUsernameAndPassword(String username, String password);
    @Query("{'username': ?0}")
    User findUserByUsername(String username);
}
