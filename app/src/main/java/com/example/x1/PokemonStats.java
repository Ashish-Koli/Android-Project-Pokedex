package com.example.x1;

import java.io.Serializable;

public class PokemonStats implements Serializable {

    String id;
    String HP;
    String Attack;
    String Defense;
    String SpecialAttack;
    String SpecialDefence;
    String Speed;

    public PokemonStats(String id, String HP, String attack, String defense, String specialAttack, String specialDefence, String speed) {
        this.id = id;
        this.HP = HP;
        Attack = attack;
        Defense = defense;
        SpecialAttack = specialAttack;
        SpecialDefence = specialDefence;
        Speed = speed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHP() {
        return HP;
    }

    public void setHP(String HP) {
        this.HP = HP;
    }

    public String getAttack() {
        return Attack;
    }

    public void setAttack(String attack) {
        Attack = attack;
    }

    public String getDefense() {
        return Defense;
    }

    public void setDefense(String defense) {
        Defense = defense;
    }

    public String getSpecialAttack() {
        return SpecialAttack;
    }

    public void setSpecialAttack(String specialAttack) {
        SpecialAttack = specialAttack;
    }

    public String getSpecialDefence() {
        return SpecialDefence;
    }

    public void setSpecialDefence(String specialDefence) {
        SpecialDefence = specialDefence;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }
}
