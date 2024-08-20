package com.myapp.root.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.myapp.root.data.BasicInfo;

public interface BasicInfoRepository  extends MongoRepository<BasicInfo, String> {
    
}
