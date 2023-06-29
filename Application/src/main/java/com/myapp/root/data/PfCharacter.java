package com.myapp.root.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("pfcharacters")
public class PfCharacter {
    @Id
    private String id;

    private String name;
    
    public PfCharacter(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public PfCharacter(String name) {
        super();
        this.name=name;
    }

    public PfCharacter() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}