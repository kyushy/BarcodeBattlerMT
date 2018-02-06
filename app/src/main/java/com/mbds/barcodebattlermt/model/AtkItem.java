package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;

/**
 * Created by Fred on 06/02/2018.
 */

@SuppressLint("ParcelCreator")
public class AtkItem extends GenFromBarCode {

    public AtkItem(){}

    public AtkItem(int atk, int type) {
        super(0, atk, 0, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
