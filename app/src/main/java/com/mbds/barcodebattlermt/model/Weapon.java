package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;

/**
 * Created by Fred on 06/02/2018.
 */

@SuppressLint("ParcelCreator")
public class Weapon extends GenFromBarCode {

    public Weapon(){}

    public Weapon(int atk) {
        super(0, atk, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
