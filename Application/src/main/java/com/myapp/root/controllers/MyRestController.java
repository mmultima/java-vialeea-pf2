package com.myapp.root.controllers;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.mongodb.client.MongoClient;
//import com.mongodb.MongoClientURI;
/* 
//import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
*/

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoIterable;
import com.myapp.root.data.PfCharacter;
import com.myapp.root.data.PfUser;
import com.myapp.root.repositories.PfCharacterRepository;
import com.myapp.root.repositories.PfUserRepository;

@RestController
@RequestMapping("/api/char")
public class MyRestController {
    @GetMapping(path="/alive")
    public boolean alive() {
        return true;
    }
    
    @GetMapping(path="/dbalive")
    public boolean dbalive() {

        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://vialeea-db:QcGXxHOMnaLJ2ej2CLPrvklVD32xlUgvmYIZ0JMLFThHB1g0EkESip3kmgVMBWvzCokMGyxkf0QJACDbdSBawQ==@vialeea-db.mongo.cosmos.azure.com:10255/?ssl=true&retrywrites=false&replicaSet=globaldb&maxIdleTimeMS=120000&appName=@vialeea-db@"));

//        MongoClient mongoClient = MongoClients.create("mongodb://vialeea-db:QcGXxHOMnaLJ2ej2CLPrvklVD32xlUgvmYIZ0JMLFThHB1g0EkESip3kmgVMBWvzCokMGyxkf0QJACDbdSBawQ==@vialeea-db.mongo.cosmos.azure.com:10255/?ssl=true&retrywrites=false&replicaSet=globaldb&maxIdleTimeMS=120000&appName=@vialeea-db@");

        //MongoClient mongoClient = MongoClients.create("mongodb://vialeea-db:QcGXxHOMnaLJ2ej2CLPrvklVD32xlUgvmYIZ0JMLFThHB1g0EkESip3kmgVMBWvzCokMGyxkf0QJACDbdSBawQ==@vialeea-db.mongo.cosmos.azure.com:10255/?ssl=true&retrywrites=false&replicaSet=globaldb&maxIdleTimeMS=120000&appName=@vialeea-db@");

        Consumer consumer = obj -> System.out.println(obj);

        //mongoClient.listDatabaseNames().forEach(System.out::println);

        mongoClient.listDatabaseNames().forEach(consumer);

        //MongoIterable myMongoIterable =  mongoClient.listDatabaseNames();

        //myMongoIterable.

//mongo-java-driver</artifactId>
//        <version>3.0.4


        return false;
    }
    @Autowired
    PfCharacterRepository pfCharacterRepository;

    @Autowired
    PfUserRepository pfUserRepository;

    @GetMapping(path="/save")
    public boolean save() {
        boolean value = false;

        try {
            //PfCharacter testChar = new PfCharacter("1", "Lindevaile Tindomë");
            PfCharacter testChar = new PfCharacter("Toavandil Hanustar");

            pfCharacterRepository.save(testChar);

            value = true;
        }
        catch(Exception e) {

        }
        return value;
    }

    @GetMapping(path="/load")
    public List<PfCharacter> load() {
 
        List<PfCharacter> value = pfCharacterRepository.findAll();

        return value;
    }

    //POST!!!
    @GetMapping(path="/newsave/{name}")
    public PfCharacter newsave(@PathVariable String name) {
        PfCharacter testChar = new PfCharacter(name);

        return  pfCharacterRepository.save(testChar);
    }

    @GetMapping(path="/loadbyuser/{name}")
    public List<PfCharacter> loadByUser(@PathVariable String name) {
        PfUser pfUser = pfUserRepository.findItemByName(name);
        if (pfUser != null) {
            return pfCharacterRepository.findAll(pfUser.getId());
        } else {
            return null;
        }
    }
}
