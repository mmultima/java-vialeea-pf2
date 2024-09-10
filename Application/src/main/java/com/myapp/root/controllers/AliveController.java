package com.myapp.root.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alive")
public class AliveController {
    @GetMapping(path="/")
    public boolean keycloak() {
        return true;
    }
}
