package com.myapp.root.data;

public class CharacterClassLong {
    //id2, name, description
    private int id;
    private String name;
    private String description;
    private int hpPerLevel;

    public CharacterClassLong(int id, String name, String description, int hpPerLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hpPerLevel = hpPerLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHpPerLevel() {
        return hpPerLevel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHpPerLevel(int hpPerLevel) {
        this.hpPerLevel = hpPerLevel;
    }
}
