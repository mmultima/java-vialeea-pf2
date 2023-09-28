package com.myapp.root.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.myapp.root.data.PfCharacterSmall;

public interface PfCharacterSmallRepository extends MongoRepository<PfCharacterSmall, String> {
    
    //@Query("{name:'?0'}")
    //PfCharacterSmall findItemByName(String name);
    
    @Query(value="{user:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<PfCharacterSmall> findAll(String user);
    
    public long count();
}
