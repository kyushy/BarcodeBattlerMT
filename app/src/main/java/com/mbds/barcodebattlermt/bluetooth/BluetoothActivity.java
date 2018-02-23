/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.mbds.barcodebattlermt.bluetooth;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.activities.FightActivity;
import com.mbds.barcodebattlermt.activities.HelperActivity;
import com.mbds.barcodebattlermt.fragments.BattleListFragment;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class BluetoothActivity extends HelperActivity {

    public static final String TAG = "BluetoothActivity";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        if (savedInstanceState == null) {
            manager = BluetoothActivity.this.getFragmentManager();

            Fragment fragment = BattleListFragment.newInstance("BT", "");
            manager.beginTransaction().replace(R.id.sample_content_fragment, fragment).commit();
        }
    }

    public void changeFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.sample_content_fragment, fragment).commit();
    }
}
