package com.myapp.root.data;

public class CharacterClass {
    private String name;
    private int id;
    private int feats;

    // Constructor
    public CharacterClass(String name, int id, int feats) {
        this.name = name;
        this.id = id;
        this.feats = feats;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for feats
    public int getFeats() {
        return feats;
    }

    // Setter for feats
    public void setFeats(int feats) {
        this.feats = feats;
    }
}