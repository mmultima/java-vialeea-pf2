package com.myapp.root.data;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("basicinfo")
public class BasicInfo {
    @Id
    private String id;
    private String charClass;
    private int level = 1;
    private int fort = 5;
    private int will = 4;
    private int ref = 6;
    private int AC = 17;
    private int HP = 14;
    private String race = "elf";
    private String gender = "Female";
    private List<String> feats;
    private List<String> weapons;
    private List<String> armor;
    private List<String> gear;
    private List<String> spells;

    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    private Skills skills;

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

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
    
    // Getter for feats
    public List<String> getFeats() {
        return feats;
    }

    // Setter for feats
    public void setFeats(List<String> feats) {
        this.feats = feats;
    }

    // Getter for weapons
    public List<String> getWeapons() {
        return weapons;
    }

    // Setter for weapons
    public void setWeapons(List<String> weapons) {
        this.weapons = weapons;
    }

    // Getter for armor
    public List<String> getArmor() {
        return armor;
    }

    // Setter for armor
    public void setArmor(List<String> armor) {
        this.armor = armor;
    }

    // Getter for gear
    public List<String> getGear() {
        return gear;
    }

    // Setter for gear
    public void setGear(List<String> gear) {
        this.gear = gear;
    }

    // Getter for spells
    public List<String> getSpells() {
        return spells;
    }

    // Setter for spells
    public void setSpells(List<String> spells) {
        this.spells = spells;
        
    }

        // Getter for strength
        public int getStrength() {
            return strength;
        }
    
        // Setter for strength
        public void setStrength(int strength) {
            this.strength = strength;
        }
    
        // Getter for dexterity
        public int getDexterity() {
            return dexterity;
        }
    
        // Setter for dexterity
        public void setDexterity(int dexterity) {
            this.dexterity = dexterity;
        }
    
        // Getter for constitution
        public int getConstitution() {
            return constitution;
        }
    
        // Setter for constitution
        public void setConstitution(int constitution) {
            this.constitution = constitution;
        }
    
        // Getter for intelligence
        public int getIntelligence() {
            return intelligence;
        }
    
        // Setter for intelligence
        public void setIntelligence(int intelligence) {
            this.intelligence = intelligence;
        }
    
        // Getter for wisdom
        public int getWisdom() {
            return wisdom;
        }
    
        // Setter for wisdom
        public void setWisdom(int wisdom) {
            this.wisdom = wisdom;
        }
    
        // Getter for charisma
        public int getCharisma() {
            return charisma;
        }
    
        // Setter for charisma
        public void setCharisma(int charisma) {
            this.charisma = charisma;
        }
    
    // Getter for skills
    public Skills getSkills() {
        return skills;
    }

    // Setter for skills
    public void setSkills(Skills skills) {
        this.skills = skills;
    }
}