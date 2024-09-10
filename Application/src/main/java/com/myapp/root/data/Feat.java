package com.myapp.root.data;

import java.util.List;

public class Feat {
    private int id;
    private String name;
    private String description;
    private List<Trait> traits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Feat(int id, String name, String description, List<Trait> traits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.traits = traits;
    }

    public Feat(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for traits
    public List<Trait> getTraits() {
        return traits;
    }

    // Setter for traits
    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }
}