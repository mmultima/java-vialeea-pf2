package com.myapp.root.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("castings")
public class Casting {
    @Id
    private String id;
    private String className;
    private int cantripCount;
    private int[] spellsPerLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCantripCount() {
        return cantripCount;
    }

    public void setCantripCount(int cantripCount) {
        this.cantripCount = cantripCount;
    }

    public int[] getSpellsPerLevel() {
        return spellsPerLevel;
    }

    public void setSpellsPerLevel(int[] spellsPerLevel) {
        this.spellsPerLevel = spellsPerLevel;
    }
}
