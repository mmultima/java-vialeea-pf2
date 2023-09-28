package com.myapp.root.data;

public class BasicInfo {
    private String charClass;
    private int level = 1;
    private int fort = 5;
    private int will = 4;
    private int ref = 6;
    private int AC = 17;
    private int HP = 14;
    private String race = "elf";
    private String gender = "Female";
    
    public String getCharClass() {
        return charClass;
    }
    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getFort() {
        return fort;
    }
    public void setFort(int fort) {
        this.fort = fort;
    }
    public int getWill() {
        return will;
    }
    public void setWill(int will) {
        this.will = will;
    }
    public int getRef() {
        return ref;
    }
    public void setRef(int ref) {
        this.ref = ref;
    }
    public int getAC() {
        return AC;
    }
    public void setAC(int aC) {
        AC = aC;
    }
    public int getHP() {
        return HP;
    }
    public void setHP(int hP) {
        HP = hP;
    }
    public String getRace() {
        return race;
    }
    public void setRace(String race) {
        this.race = race;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }   
}
