package com.mbds.barcodebattlermt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mbds.barcodebattlermt.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class GameActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_scan:
                    mTextMessage.setText(R.string.title_scan);
                    IntentIntegrator barScan = new IntentIntegrator(GameActivity.this);
                    Collection<String> s = new ArrayList<>();
                    s.addAll(IntentIntegrator.PRODUCT_CODE_TYPES);
                    s.addAll(IntentIntegrator.ONE_D_CODE_TYPES);
                    barScan.initiateScan(s);
                    return true;
                case R.id.navigation_fight:
                    mTextMessage.setText(R.string.title_fight);
                    return true;
                case R.id.navigation_monsters:
                    mTextMessage.setText(R.string.title_monsters);
                    Intent intent = new Intent(GameActivity.this, BattlerListActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_gears:
                    mTextMessage.setText(R.string.title_gears);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_b);

        mTextMessage = (TextView) findViewById(R.id.message);
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
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Log.v("Test", "" + requestCode);
        }
    }

}