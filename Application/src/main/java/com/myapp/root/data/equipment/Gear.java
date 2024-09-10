package com.myapp.root.data.equipment;

import java.util.List;

public class Gear {
    private int id;
    private String name;
    private String description;
    private int subId;
    private List<String> subItemNames;
    private List<Integer> pricesInCopper;
    private List<String> bulks;

    public Gear(int id2, String name, String description) {
        this.id = id2;
        this.name = name;
        this.description = description;
    }

    public Gear() {
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

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public List<String> getSubItemNames() {
        return subItemNames;
    }

    public void setSubItemNames(List<String> subItemNames) {
        this.subItemNames = subItemNames;
    }

    public List<Integer> getPricesInCopper() {
        return pricesInCopper;
    }

    public void setPricesInCopper(List<Integer> pricesInCopper) {
        this.pricesInCopper = pricesInCopper;
    }

    public List<String> getBulks() {
        return bulks;
    }

    public void setBulks(List<String> bulks) {
        this.bulks = bulks;
    }
}
