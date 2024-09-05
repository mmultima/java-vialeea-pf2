package com.myapp.root.data.equipment;

public class Weapon {
    private int id;
    private String name;
    private String description;
    private int priceInCopper;
    private String damage;
    private String bulk;
    private String hands;
    private String range;
    private String reload;
    private String type;
    private String category;
    private String group;
    private String twoHandedDamage;
    private int deadlyDice;

    private int purchaseAmount;

    public Weapon(int id2, String name, String description, int priceInCopper, String damage, String bulk, String hands, String range, String reload, String type, String category, String group, String twoHandedDamage) {
        this.id = id2;
        this.name = name;
        this.description = description;
        this.priceInCopper = priceInCopper;
        this.damage = damage;
        this.bulk = bulk;
        this.hands = hands;
        this.range = range;
        this.reload = reload;
        this.type = type;
        this.category = category;
        this.group = group;
        this.twoHandedDamage = twoHandedDamage;
    }

    public Weapon(int id2, String name, String description) {
        this.id = id2;
        this.name = name;
        this.description = description;
    }

    public Weapon() {
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

    public int getPriceInCopper() {
        return priceInCopper;
    }

    public String getDamage() {
        return damage;
    }

    public String getBulk() {
        return bulk;
    }

    public String getHands() {
        return hands;
    }

    public String getRange() {
        return range;
    }

    public String getReload() {
        return reload;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getGroup() {
        return group;
    }

    public String getTwoHandedDamage() {
        return twoHandedDamage;
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

    public void setPriceInCopper(int priceInCopper) {
        this.priceInCopper = priceInCopper;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public void setBulk(String bulk) {
        this.bulk = bulk;
    }

    public void setHands(String hands) {
        this.hands = hands;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public void setReload(String reload) {
        this.reload = reload;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setTwoHandedDamage(String twoHandedDamage) {
        this.twoHandedDamage = twoHandedDamage;
    }

    public int getDeadlyDice() {
        return deadlyDice;
    }

    public void setDeadlyDice(int deadlyDice) {
        this.deadlyDice = deadlyDice;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }
}