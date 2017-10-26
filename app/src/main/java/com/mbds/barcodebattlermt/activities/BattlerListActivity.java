package com.mbds.barcodebattlermt.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.model.Battler;
import com.mbds.barcodebattlermt.model.BattlerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BattlerListActivity extends AppCompatActivity {

    private ListView battlersView;
    private List<Battler> battlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battler_list);

        battlersView = (ListView) findViewById(R.id.battlers_list);
        battlers = new ArrayList<>();
        battlers.add(new Battler(15, 10, 10));

        BattlerAdapter adapter = new BattlerAdapter(BattlerListActivity.this, battlers);
        battlersView.setAdapter(adapter);
    }
}
