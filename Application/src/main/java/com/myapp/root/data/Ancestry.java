package com.myapp.root.data;

import java.util.List;

public class Ancestry {
    private String name;
    private int id;
    private String rarity;
    private List<Trait> traits;

    public Ancestry(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Ancestry() {
        //TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }
}
