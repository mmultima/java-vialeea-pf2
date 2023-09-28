package com.myapp.root.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.myapp.root.data.PfCharacter;
import com.myapp.root.data.PfUser;
import com.myapp.root.repositories.PfCharacterRepository;
import com.myapp.root.repositories.PfUserRepository;

@RestController
@RequestMapping("/api/user")
public class PfUserController {
    @Autowired
    private PfUserRepository pfUserRepository;

    @Autowired
    private PfCharacterRepository pfCharacterRepository;

    @GetMapping("/save/{name}")
    public PfUser savenew(@PathVariable String name) {
        PfUser user = new PfUser();
        user.setName(name);

        return pfUserRepository.save(user);
    }

    @GetMapping("/change/{pfcharacter}/{user}")
    public PfCharacter changeUser(@PathVariable String pfcharacter, @PathVariable String user) {
        PfCharacter pfCharacter2 = pfCharacterRepository.findItemByName(pfcharacter);
        PfUser user2 = pfUserRepository.findItemByName(user);

        if (pfCharacter2 != null) {
            pfCharacter2.setUser(user2.getId());
            return pfCharacterRepository.save(pfCharacter2);
        } else {
            return null;
        }
    }


    @GetMapping(path="/load")
    public List<PfUser> load() {
 
        List<PfUser> value = pfUserRepository.findAll();

        return value;
    }

    @PostMapping("/testsave")
    public PfUser testsavenew(@RequestBody PfUser pfUser) {
        //System.err.println("Hello!");
        return pfUser;
    }    
}
