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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.hp);
        dest.writeInt(this.atk);
        dest.writeInt(this.def);
        dest.writeInt(this.type);
    }

    protected GenFromBarCode(Parcel in) {
        this.id = in.readInt();
        this.hp = in.readInt();
        this.atk = in.readInt();
        this.def = in.readInt();
        this.type = in.readInt();
    }
}
