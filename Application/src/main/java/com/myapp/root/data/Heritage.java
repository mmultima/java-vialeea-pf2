package com.myapp.root.data;

public class Heritage {
    private int id;
    private String name;

    public Heritage(String name) {
        this.name = name;
    }

    public Heritage(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Heritage() {
        //TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
