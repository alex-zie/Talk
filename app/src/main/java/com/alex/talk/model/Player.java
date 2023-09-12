package com.alex.talk.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A class for a more general Person object used for Recycler View
 */
public class Player implements Serializable {
    private long id;

    private String name;
    private byte gender;
    private int color;

    public Player(){
        //Default Constructor
    }

    public Player(long id, String name, byte gender, int color) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && gender == player.gender && color == player.color && Objects.equals(name, player.name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", color=" + color +
                '}';
    }
}
