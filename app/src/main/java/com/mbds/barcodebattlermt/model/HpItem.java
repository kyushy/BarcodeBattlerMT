package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;

/**
 * Created by Fred on 06/02/2018.
 */

@SuppressLint("ParcelCreator")
public class HpItem extends GenFromBarCode {

    public HpItem() {}

    public HpItem(int hp, int type) {
        super(hp, 0, 0, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
