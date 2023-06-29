package com.myapp.root.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.myapp.root.data.PfUser;

public interface PfUserRepository extends MongoRepository<PfUser, String> {
    @Query("{name:'?0'}")
    PfUser findItemByName(String name);
}
