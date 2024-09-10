package com.myapp.root.data;

public class Background {
    private int id;
    private String name;
    private String description;

    public Background(int id2, String name, String description) {
        this.id = id2;
        this.name = name;
        this.description = description;
    }

    public Background() {
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
