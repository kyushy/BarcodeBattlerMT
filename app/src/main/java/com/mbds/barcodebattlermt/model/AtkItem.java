package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;
import android.os.Parcel;

/**
 * Created by Fred on 06/02/2018.
 */

public class AtkItem extends GenFromBarCode {

    public AtkItem(){}

    public AtkItem(int atk, int type) {
        super(0, atk, 0, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public AtkItem(Parcel in) {
        super(in);
    }

    public static final Creator<AtkItem> CREATOR = new Creator<AtkItem>() {
        @Override
        public AtkItem createFromParcel(Parcel source) {
            return new AtkItem(source);
        }

        @Override
        public AtkItem[] newArray(int size) {
            return new AtkItem[size];
        }
    };
}
