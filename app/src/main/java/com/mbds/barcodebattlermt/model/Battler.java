package com.mbds.barcodebattlermt.model;

import android.os.Parcel;

/**
 * Created by Fred on 25/10/2017.
 */

public class Battler extends GenFromBarCode {

    private int level = 1;

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

    @Override
    public String toString() {
        return "Battler{" +
                super.toString() +
                ", level=" + level +
                '}';
    }

    public Battler(Parcel source) {
        super(source);
        level = source.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(level);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
