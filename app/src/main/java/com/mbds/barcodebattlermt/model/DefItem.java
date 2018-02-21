package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;
import android.os.Parcel;

/**
 * Created by Fred on 06/02/2018.
 */

public class DefItem extends GenFromBarCode {

    public DefItem() {}

    public DefItem(int def, int type) {
        super(0, 0, def, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public DefItem(Parcel in) {
        super(in);
    }

    public static final Creator<DefItem> CREATOR = new Creator<DefItem>() {
        @Override
        public DefItem createFromParcel(Parcel source) {
            return new DefItem(source);
        }

        @Override
        public DefItem[] newArray(int size) {
            return new DefItem[size];
        }
    };
}
