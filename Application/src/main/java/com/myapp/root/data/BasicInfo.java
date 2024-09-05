package com.myapp.root.data;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.myapp.root.data.equipment.GearCompact;

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
    private List<GearCompact> gearCompact;

    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    private int pfs;
    private String faction;
    private String xpProgression;


    private Skills skills;

    private int speed;

    private String explorationMode;

    private int focusPoints;

    private int ancestry;

    private int heritage;

    private int background;

    private String size;

    private boolean lowLightVision;
    private boolean darkVision;
    private boolean greaterDarkVision;
    private boolean scent;
    private boolean tremorsense;

    private String simple;
    private String martial;

    private String muse;

    private String tradition; //TODO: Move to Castings

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

    // Getter for gearCompact
    public List<GearCompact> getGearCompact() {
        return gearCompact;
    }

    // Setter for gearCompact
    public void setGearCompact(List<GearCompact> gearCompact) {
        this.gearCompact = gearCompact;
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

    // Getter for pfs
    public int getPfs() {
        return pfs;
    }

    // Setter for pfs
    public void setPfs(int pfs) {
        this.pfs = pfs;
    }

    // Getter for faction
    public String getFaction() {
        return faction;
    }

    // Setter for faction
    public void setFaction(String faction) {
        this.faction = faction;
    }

    // Getter for xpProgression
    public String getXpProgression() {
        return xpProgression;
    }

    // Setter for xpProgression
    public void setXpProgression(String xpProgression) {
        this.xpProgression = xpProgression;
    }

    // Getter for speed
    public int getSpeed() {
        return speed;
    }

    // Setter for speed
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Getter for explorationMode
    public String getExplorationMode() {
        return explorationMode;
    }

    // Setter for explorationMode
    public void setExplorationMode(String explorationMode) {
        this.explorationMode = explorationMode;
    }

    // Getter for focusPoints
    public int getFocusPoints() {
        return focusPoints;
    }

    // Setter for focusPoints
    public void setFocusPoints(int focusPoints) {
        this.focusPoints = focusPoints;
    }

    // Getter for ancestry
    public int getAncestry() {
        return ancestry;
    }

    // Setter for ancestry 
    public void setAncestry(int ancestry) {
        this.ancestry = ancestry;
    }

    // Getter for heritage
    public int getHeritage() {
        return heritage;
    }

    // Setter for heritage
    public void setHeritage(int heritage) {
        this.heritage = heritage;
    }

    // Getter for background
    public int getBackground() {
        return background;
    }

    // Setter for background
    public void setBackground(int background) {
        this.background = background;
    }

    // Getter for size
    public String getSize() {
        return size;
    }

    // Setter for size
    public void setSize(String size) {
        this.size = size;
    }

    // Getter for lowLightVision
    public boolean isLowLightVision() {
        return lowLightVision;
    }

    // Setter for lowLightVision
    public void setLowLightVision(boolean lowLightVision) {
        this.lowLightVision = lowLightVision;
    }

    // Getter for darkVision
    public boolean isDarkVision() {
        return darkVision;
    }

    // Setter for darkVision
    public void setDarkVision(boolean darkVision) {
        this.darkVision = darkVision;
    }

    // Getter for greaterDarkVision
    public boolean isGreaterDarkVision() {
        return greaterDarkVision;
    }

    // Setter for greaterDarkVision
    public void setGreaterDarkVision(boolean greaterDarkVision) {
        this.greaterDarkVision = greaterDarkVision;
    }

    // Getter for scent
    public boolean isScent() {
        return scent;
    }

    // Setter for scent
    public void setScent(boolean scent) {
        this.scent = scent;
    }

    // Getter for tremorsense
    public boolean isTremorsense() {
        return tremorsense;
    }

    // Setter for tremorsense
    public void setTremorsense(boolean tremorsense) {
        this.tremorsense = tremorsense;
    }

    // Getter for simple
    public String getSimple() {
        return simple;
    }

    // Setter for simple
    public void setSimple(String simple) {
        this.simple = simple;
    }

    // Getter for martial
    public String getMartial() {
        return martial;
    }

    // Setter for martial
    public void setMartial(String martial) {
        this.martial = martial;
    }

    // Getter for muse
    public String getMuse() {
        return muse;
    }

    // Setter for muse
    public void setMuse(String muse) {
        this.muse = muse;
    }

    // Getter for tradition
    public String getTradition() {
        return tradition;
    }

    // Setter for tradition
    public void setTradition(String tradition) {
        this.tradition = tradition;
    }
}