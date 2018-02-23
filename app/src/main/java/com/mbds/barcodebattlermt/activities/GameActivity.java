package com.mbds.barcodebattlermt.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.controler.Controler;
import com.mbds.barcodebattlermt.fragments.BattleListFragment;
import com.mbds.barcodebattlermt.fragments.ChoiceFightFragment;
import com.mbds.barcodebattlermt.fragments.GearListFragment;
import com.mbds.barcodebattlermt.fragments.HomeFragment;
import com.mbds.barcodebattlermt.model.AtkItem;
import com.mbds.barcodebattlermt.model.Battler;
import com.mbds.barcodebattlermt.model.DefItem;
import com.mbds.barcodebattlermt.model.GenFromBarCode;
import com.mbds.barcodebattlermt.model.HpItem;
import com.mbds.barcodebattlermt.model.Potion;

import java.util.ArrayList;
import java.util.Collection;

// UID pout bt : 2,5
public class GameActivity extends HelperActivity {

    private FragmentManager manager;
    private Fragment fragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = HomeFragment.newInstance("", "");
                    manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    return true;
                case R.id.navigation_scan:
                    IntentIntegrator barScan = new IntentIntegrator(GameActivity.this);
                    Collection<String> s = new ArrayList<>();
                    s.addAll(IntentIntegrator.PRODUCT_CODE_TYPES);
                    s.addAll(IntentIntegrator.ONE_D_CODE_TYPES);
                    barScan.initiateScan(s);
                    return true;
                case R.id.navigation_fight:
                    fragment = ChoiceFightFragment.newInstance("", "");
                    manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    return true;
                case R.id.navigation_monsters:
                    fragment = BattleListFragment.newInstance("", "");
                    manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    return true;
                case R.id.navigation_gears:
                    fragment = GearListFragment.newInstance("", "");
                    manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_b);

        manager = GameActivity.this.getFragmentManager();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                String r = result.getContents();
                if (r.length() < 8) {
                    throw new IllegalArgumentException("Barcode with less than 8 characters.");
                } else {
                    String bar = r.substring(Math.max(0, r.length() - 8));
                    GenFromBarCode g = Controler.generate(bar);
                    Log.v("Test", g.toString());
                    if(g instanceof Battler) {
                        getHelper().addBattler((Battler)g);
                    }
                    else if (g instanceof HpItem) {
                        getHelper().addHpItem((HpItem)g);
                    }
                    else if (g instanceof AtkItem) {
                        getHelper().addAtkItem((AtkItem)g);
                    }
                    else if (g instanceof DefItem) {
                        getHelper().addDefItem((DefItem)g);
                    }
                    else {
                        getHelper().addPotion((Potion)g);
                    }
                }
            }
        } else {
            Log.v("Test", "" + requestCode);
        }
    }

    public void changeFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

}
