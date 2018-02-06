package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;

/**
 * Created by Fred on 06/02/2018.
 */

@SuppressLint("ParcelCreator")
public class Potion extends GenFromBarCode {

    public Potion() {}

    public Potion(int hp, int atk, int def) {
        super(hp, atk, def);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
