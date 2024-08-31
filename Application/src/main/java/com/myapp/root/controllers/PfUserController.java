package com.myapp.root.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.myapp.root.data.PfCharacter;
import com.myapp.root.data.PfUser;
import com.myapp.root.repositories.PfCharacterRepository;
import com.myapp.root.repositories.PfUserRepository;

@RestController
@RequestMapping("/api/users")
public class PfUserController {
    @Autowired
    private PfUserRepository pfUserRepository;

    @Autowired
    private PfCharacterRepository pfCharacterRepository;

    /*
    @GetMapping("/save/{name}")
    public PfUser savenew(@PathVariable String name) {
        PfUser user = new PfUser();
        user.setName(name);

        return pfUserRepository.save(user);
    }
*/

/*
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
*/

    @PostMapping(path="")
    public PfUser save(@RequestBody PfUser pfUser) {
        System.out.println("Saving user: " + pfUser.getName());
        return pfUserRepository.save(pfUser);
    }

    @GetMapping(path="")
    public List<PfUser> loadAll() {
 
        List<PfUser> value = pfUserRepository.findAll();

        return value;
    }

    /*
    @PostMapping("/testsave")
    public PfUser testsavenew(@RequestBody PfUser pfUser) {
        //System.err.println("Hello!");
        return pfUser;
    }
        */

    @GetMapping("/{id}")
    public PfUser load(@PathVariable String id) {
        return pfUserRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public PfUser update(@PathVariable String id, @RequestBody PfUser pfUser) {
        PfUser user = pfUserRepository.findById(id).orElse(null);

        if (user != null) {
            user.setName(pfUser.getName());
            user.setPfs(pfUser.getPfs());
            return pfUserRepository.save(user);
        } else {
            return null;
        }
    }
}
