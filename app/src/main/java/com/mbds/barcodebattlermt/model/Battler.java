package com.mbds.barcodebattlermt.model;

/**
 * Created by Fred on 25/10/2017.
 */

public class Battler {

    private int id;
    private int hp;
    private int atk;
    private int def;

    public Battler() {}

    public Battler(int hp, int atk, int def) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
}
