package com.mbds.barcodebattlermt.model;

import android.annotation.SuppressLint;

/**
 * Created by Fred on 06/02/2018.
 */

@SuppressLint("ParcelCreator")
public class DefItem extends GenFromBarCode {

    public DefItem() {}

    public DefItem(int def, int type) {
        super(0, 0, def, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
