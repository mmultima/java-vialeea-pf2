package com.myapp.root.data.equipment;

public class GearCompact {
    private int id;
    private String name;
    private int subId;

    public GearCompact(int id2, String name, int subId) {
        this.id = id2;
        this.name = name;
    }

    public GearCompact() {
        //TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    
}
