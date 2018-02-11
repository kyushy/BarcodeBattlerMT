package com.mbds.barcodebattlermt.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.fragments.BattleListFragment;

public class FightActivity extends HelperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        FragmentManager manager = FightActivity.this.getFragmentManager();
        Fragment fragment = BattleListFragment.newInstance("", "");
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

}
