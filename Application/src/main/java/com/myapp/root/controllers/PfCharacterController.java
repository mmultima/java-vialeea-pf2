package com.myapp.root.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.root.data.BasicInfo;
import com.myapp.root.data.PfCharacter;
import com.myapp.root.data.PfUser;
import com.myapp.root.repositories.PfCharacterRepository;
import com.myapp.root.repositories.PfUserRepository;


@RestController
@RequestMapping("/api/pfcharacters")
public class PfCharacterController {
    @Autowired
    PfCharacterRepository pfCharacterRepository;
    
    @Autowired
    PfUserRepository pfUserRepository;   

    /*
    @GetMapping(path="/save")
    public boolean save() {
        boolean value = false;

        try {
            PfCharacter testChar = new PfCharacter("Toavandil Hanustar");

            pfCharacterRepository.save(testChar);

            value = true;
        }
        catch(Exception e) {

        }
        return value;
    }
    */

    @GetMapping(path="")
    public List<PfCharacter> loadAll() {
 
        List<PfCharacter> value = pfCharacterRepository.findAll();

        return value;
    }

    @GetMapping(path="/{id}")
    public PfCharacter load(@PathVariable String id) {
 
        Optional<PfCharacter> value = pfCharacterRepository.findById(id);

        if (value.isPresent()) {
            return value.get();
        }

        /* How do we return correct HTML error? */
        return null;
    }

    @PostMapping(path="")
    public PfCharacter save(@RequestBody PfCharacter pfCharacter) {


        //System.out.println("########## pathvariable " + id);

        System.out.println("########## name " + pfCharacter.getName());
        System.out.println("########## id " + pfCharacter.getId());
        System.out.println("########## image " + pfCharacter.getImage());
        System.out.println("########## colour " + pfCharacter.getColour());
        System.out.println("########## user " + pfCharacter.getUser());

        //PfCharacter testChar = new PfCharacter(name);

        //return  pfCharacterRepository.save(testChar);

        pfCharacter.setName("Changed name");

        return pfCharacter;
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

    @PutMapping("/{id}")
    public PfCharacter update(@RequestBody PfCharacter pfCharacter, @PathVariable String id) {
        System.out.println("########## pathvariable " + id);

        System.out.println("########## name " + pfCharacter.getName());
        System.out.println("########## id " + pfCharacter.getId());
        System.out.println("########## image " + pfCharacter.getImage());
        System.out.println("########## colour " + pfCharacter.getColour());
        System.out.println("########## user " + pfCharacter.getUser());


        //System.out.println(pfCharacter);


        //PfCharacter testChar = new PfCharacter(name);

        //return  pfCharacterRepository.save(testChar);

        //pfCharacter.setName("Changed name for id " + id);

        pfCharacterRepository.save(pfCharacter);

        //System.out.println("##########" + pfCharacter.getColour());



        return pfCharacter;
    }

    @PutMapping("/basicinfo/{id}")
    public BasicInfo updateBasicInfo(@RequestBody BasicInfo basicInfo, @PathVariable String id) {
        return basicInfo;
    }
}

