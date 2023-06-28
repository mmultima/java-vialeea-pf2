package com.myapp.root.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyRestController {
    @GetMapping(path="/alive")
    public boolean alive() {
        return true;
    }
    
}
