package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;

/**
 * Created by Fred on 06/02/2018.
 */

@SuppressLint("ParcelCreator")
public class Armor extends GenFromBarCode {

    public Armor() {}

    public Armor(int def) {
        super(0, 0, def);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
