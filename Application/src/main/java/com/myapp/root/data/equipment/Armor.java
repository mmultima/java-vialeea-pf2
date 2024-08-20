package com.myapp.root.data.equipment;

public class Armor {
    private int id;
    private String name;
    private String description;

    public Armor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Armor(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Armor() {
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
}
