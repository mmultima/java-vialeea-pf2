package com.myapp.root.data;

public class Skill {
    private String name;
    private int rank;
    private int misc;
    private int total;

    public Skill(String name, int rank, int misc) {
        this.name = name;
        this.rank = rank;
        this.misc = misc;
        this.total = rank + misc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMisc() {
        return misc;
    }

    public void setMisc(int misc) {
        this.misc = misc;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
