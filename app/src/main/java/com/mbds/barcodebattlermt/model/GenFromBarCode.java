package com.mbds.barcodebattlermt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JuIngong on 26/10/2017.
 */

public abstract class GenFromBarCode implements Parcelable {
    private int id;
    private int hp;
    private int atk;
    private int def;
    private int type;

    public GenFromBarCode() {
    }

    public GenFromBarCode(int hp, int atk, int def) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }

    public GenFromBarCode(int hp, int atk, int def, int type) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.type = type;
    }

    public GenFromBarCode(Parcel source) {
        id = source.readInt();
        hp = source.readInt();
        atk = source.readInt();
        def = source.readInt();
        type = source.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(hp);
        dest.writeInt(atk);
        dest.writeInt(def);
        dest.writeInt(type);
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GenFromBarCode{" +
                "id=" + id +
                ", hp=" + hp +
                ", atk=" + atk +
                ", def=" + def +
                ", type=" + type +
                '}';
    }
}
