package com.myapp.root.data.equipment;

public class Armor {
    private int id;
    private String name;
    private String description;

    private int priceInCopper;
    private int acBonus;
    private int dexCap;
    private int checkPenalty;
    private int speedPenalty;
    private int strength;

    private String bulk;
    private String category;
    private String group;

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

    public int getPriceInCopper() {
        return priceInCopper;
    }

    public void setPriceInCopper(int priceInCopper) {
        this.priceInCopper = priceInCopper;
    }

    public int getAcBonus() {
        return acBonus;
    }

    public void setAcBonus(int acBonus) {
        this.acBonus = acBonus;
    }

    public int getDexCap() {
        return dexCap;
    }

    public void setDexCap(int dexCap) {
        this.dexCap = dexCap;
    }

    public int getCheckPenalty() {
        return checkPenalty;
    }

    public void setCheckPenalty(int checkPenalty) {
        this.checkPenalty = checkPenalty;
    }

    public int getSpeedPenalty() {
        return speedPenalty;
    }

    public void setSpeedPenalty(int speedPenalty) {
        this.speedPenalty = speedPenalty;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getBulk() {
        return bulk;
    }

    public void setBulk(String bulk) {
        this.bulk = bulk;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
