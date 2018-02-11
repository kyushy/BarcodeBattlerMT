package com.mbds.barcodebattlermt.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.model.Helper;

public class HelperActivity extends AppCompatActivity {

    private Helper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        myHelper = new Helper(this);
    }

    public Helper getHelper() {
        return myHelper;
    }

    @Override
    protected void onDestroy() {
        myHelper.close();
        super.onDestroy();
    }
}
