package com.myapp.root.data;

import java.util.List;

public class Spell {
    private int id;
    private String name;
    private String description;
    private int level;
    private String tradition;
    /*
    private boolean isCantrip;
    private boolean isComposition;
    private boolean isFocus;
*/

    private List<Trait> traits;

    public Spell(int id2, String name, String description) {
        this.id = id2;
        this.name = name;
        this.description = description;
    }

    public Spell() {
        //TODO Auto-generated constructor stub
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTradition() {
        return tradition;
    }

    public void setTradition(String tradition) {
        this.tradition = tradition;
    }
/*
    public boolean isCantrip() {
        return isCantrip;
    }

    public void setCantrip(boolean isCantrip) {
        this.isCantrip = isCantrip;
    }

    public boolean isComposition() {
        return isComposition;
    }

    public void setComposition(boolean isComposition) {
        this.isComposition = isComposition;
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean isFocus) {
        this.isFocus = isFocus;
    }
*/
    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }
}
