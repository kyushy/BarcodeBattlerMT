package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;
import android.os.Parcel;

/**
 * Created by Fred on 06/02/2018.
 */

public class HpItem extends GenFromBarCode {

    public HpItem() {}

    public HpItem(int hp, int type) {
        super(hp, 0, 0, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public HpItem(Parcel in) {
        super(in);
    }

    public static final Creator<HpItem> CREATOR = new Creator<HpItem>() {
        @Override
        public HpItem createFromParcel(Parcel source) {
            return new HpItem(source);
        }

        @Override
        public HpItem[] newArray(int size) {
            return new HpItem[size];
        }
    };
}
