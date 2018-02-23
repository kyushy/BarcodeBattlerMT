package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;
import android.os.Parcel;

/**
 * Created by Fred on 25/10/2017.
 */

public class Battler extends GenFromBarCode {

    private int level = 1;

    private HpItem hpItem;
    private AtkItem atkItem;
    private DefItem defItem;

    public Battler() {
    }

    public Battler(int hp, int atk, int def) {
        super(hp, atk, def);
    }

    public Battler(int hp, int atk, int def, int type) {
        super(hp, atk, def, type);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public HpItem getHpItem() {
        return hpItem;
    }

    public void setHpItem(HpItem hpItem) {
        this.hpItem = hpItem;
    }

    public AtkItem getAtkItem() {
        return atkItem;
    }

    public void setAtkItem(AtkItem atkItem) {
        this.atkItem = atkItem;
    }

    public DefItem getDefItem() {
        return defItem;
    }

    public void setDefItem(DefItem defItem) {
        this.defItem = defItem;
    }

    @Override
    public String toString() {
        return "Battler{" +
                super.toString() +
                ", level=" + level +
                '}';
    }

    public int getLvlHp() {
        return (int)(getHp()+ getHp()*level*0.1);
    }

    public int getLvlAtk() {
        return (int)(getAtk()+ getAtk()*level*0.1);
    }

    public int getLvlDef() {
        return (int)(getDef()+ getDef()*level*0.1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.level);
    }

    public Battler(Parcel in) {
        super(in);
        this.level = in.readInt();
    }

    public static final Creator<Battler> CREATOR = new Creator<Battler>() {
        @Override
        public Battler createFromParcel(Parcel source) {
            return new Battler(source);
        }

        @Override
        public Battler[] newArray(int size) {
            return new Battler[size];
        }
    };
}
