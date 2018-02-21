package com.mbds.barcodebattlermt.model;

import android.os.Parcel;

/**
 * Created by Fred on 06/02/2018.
 */

public class Potion extends GenFromBarCode {

    public Potion() {}

    public Potion(int hp, int atk, int def, int type) {
        super(hp, atk, def, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public Potion(Parcel in) {
        super(in);
    }

    public static final Creator<Potion> CREATOR = new Creator<Potion>() {
        @Override
        public Potion createFromParcel(Parcel source) {
            return new Potion(source);
        }

        @Override
        public Potion[] newArray(int size) {
            return new Potion[size];
        }
    };
}
