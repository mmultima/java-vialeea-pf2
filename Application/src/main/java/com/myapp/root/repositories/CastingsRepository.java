package com.myapp.root.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.myapp.root.data.Casting;

public interface CastingsRepository extends MongoRepository<Casting, String> {

}
