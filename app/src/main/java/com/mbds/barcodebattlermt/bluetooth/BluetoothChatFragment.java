/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mbds.barcodebattlermt.bluetooth;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.activities.FightActivity;
import com.mbds.barcodebattlermt.activities.HelperActivity;
import com.mbds.barcodebattlermt.fragments.ResultFragment;
import com.mbds.barcodebattlermt.model.Battler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This fragment controls Bluetooth to communicate with other devices.
 */
public class BluetoothChatFragment extends Fragment {

    private static final String TAG = "BluetoothChatFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;


    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    private BluetoothChatService mChatService = null;

    private Button btnSec;

    private Button btnInsec;

    private Button btnDisco;

    private Button atk;
    private Button riposte;
    private Button special;
    private Button potion;
    private ImageView ally;
    private ImageView enemy;

    private ProgressBar hpa;
    private ProgressBar hpe;

    private TextView dmga;
    private TextView dmge;

    private String otherAction;

    private RelativeLayout rl;


    private boolean finish = false;


    private Battler mine;
    private Battler bad;

    public static BluetoothChatFragment newInstance(String param1, String param2) {
        BluetoothChatFragment fragment = new BluetoothChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(getActivity(), "Bluetooth is not available", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                System.out.println("test");
                mChatService.start();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bluetooth_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        btnSec = (Button) view.findViewById(R.id.secure_connect_scan);
        btnInsec = (Button) view.findViewById(R.id.insecure_connect_scan);
        btnDisco = (Button) view.findViewById(R.id.discoverable);

        rl = (RelativeLayout) view.findViewById(R.id.monster_layout);

        atk = (Button) view.findViewById(R.id.atk);
        riposte = (Button) view.findViewById(R.id.riposte);
        special = (Button) view.findViewById(R.id.special);
        potion = (Button) view.findViewById(R.id.potion);

        ally = (ImageView) view.findViewById(R.id.imageView);
        enemy = (ImageView) view.findViewById(R.id.imageView2);
        hpa = (ProgressBar) view.findViewById(R.id.progressBar);
        hpe = (ProgressBar) view.findViewById(R.id.progressBar2);

        dmga = (TextView) view.findViewById(R.id.dmg1);
        dmge = (TextView) view.findViewById(R.id.dmg2);

        mine = ((HelperActivity) getActivity()).getHelper().getBattler(mParam1);
        int id = getResources().getIdentifier("sprite_" + (mine.getType() + 1), "drawable", "com.mbds.barcodebattlermt");
        ally.setImageResource(id);

        hpa.setMax(mine.getLvlHp());
        hpa.setProgress(mine.getLvlHp());
        otherAction="";

        atk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fight("atk");
            }
        });

        riposte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fight("rpt");
            }
        });

        special.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fight("spl");
            }
        });

        potion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Potion");
            }
        });
    }

    public void fight(final String action) {
        disableButton();
        if(!otherAction.equals("")) {
            final int atka = new Random().nextInt(mine.getLvlAtk() + 1);
            final int defa = new Random().nextInt(mine.getLvlDef() + 1);
            final int atke = new Random().nextInt(bad.getLvlAtk() + 1);
            final int defe = new Random().nextInt(bad.getLvlDef() + 1);
            Animation animation;
            AnimationSet s;
            switch (action) {
                case "atk":
                    switch (otherAction) {
                        case "atk":
                            animation = new TranslateAnimation(0, 200, 0, -150);
                            animation.setDuration(500);
                            animation.setAnimationListener(
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            Animation animation2 = new TranslateAnimation(0, -200, 0, 150);
                                            animation2.setDuration(500);
                                            enemy.startAnimation(animation2);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {

                                            neutre(atka, defa, atke, defe, action);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    }
                            );
                            ally.startAnimation(animation);

                            break;
                        case "rpt":
                            animation = new TranslateAnimation(0, 200, 0, -150);
                            animation.setDuration(500);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    Animation animation2 = new TranslateAnimation(0, -400, 0, 300);
                                    animation2.setDuration(500);
                                    animation2.setStartOffset(500);
                                    enemy.startAnimation(animation2);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    deffective(atka, defa, atke, action);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ally.startAnimation(animation);

                            break;
                        case "spl":
                            animation = new TranslateAnimation(0, 400, 0, -300);
                            animation.setDuration(500);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    enemy.startAnimation(splAnim());
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                    effective(atka, atke, defe, action);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ally.startAnimation(animation);
                            break;
                    }
                    break;
                case "rpt":
                    switch (otherAction) {
                        case "atk":
                            //anim riposte
                            animation = new TranslateAnimation(0, 400, 0, -300);
                            animation.setStartOffset(500);
                            animation.setDuration(500);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                                               @Override
                                                               public void onAnimationStart(Animation animation) {
                                                                   //anim atk
                                                                   Animation animation2 = new TranslateAnimation(0, -200, 0, 150);
                                                                   animation2.setDuration(500);
                                                                   enemy.startAnimation(animation2);
                                                               }

                                                               @Override
                                                               public void onAnimationEnd(Animation animation) {
                                                                   effective(atka, atke, defe, action);
                                                               }

                                                               @Override
                                                               public void onAnimationRepeat(Animation animation) {

                                                               }
                                                           }

                            );
                            ally.startAnimation(animation);
                            break;
                        case "rpt":
                            neutre(0, defa, 0, defe, action);
                            break;
                        case "spl":
                            s = splAnim();
                            s.setAnimationListener(
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            deffective(atka, defa, atke, action);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    }
                            );
                            enemy.startAnimation(s);

                            break;
                    }

                    break;
                case "spl":
                    switch (otherAction) {
                        case "atk":
                            s = splAnim();
                            s.setAnimationListener(
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            Animation animation2 = new TranslateAnimation(0, -200, 0, 150);
                                            animation2.setDuration(500);
                                            enemy.startAnimation(animation2);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            deffective(atka, defa, atke, action);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    }
                            );
                            ally.startAnimation(s);

                            break;
                        case "rpt":
                            s = splAnim();
                            s.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    effective(atka, atke, defe, action);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ally.startAnimation(s);
                            break;
                        case "spl":
                            s = splAnim();
                            s.setAnimationListener(
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            enemy.startAnimation(splAnim());
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            neutre(atka, defa, atke, defe, action);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    }
                            );
                            ally.startAnimation(s);

                            break;
                    }

                    break;
            }
        }
        else{
            Map<String, String> m = new HashMap<>();
            m.put("pony", "action");
            m.put("action", action);
            sendMessage(m);
        }
    }


    public void fakeFight(final String action, final String actionOther, final int dmgMe, final int dmgYou) {
            Animation animation;
            AnimationSet s;
            switch (action) {
                case "atk":
                    switch (actionOther) {
                        case "atk":
                            animation = new TranslateAnimation(0, 200, 0, -150);
                            animation.setDuration(500);
                            animation.setAnimationListener(
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            Animation animation2 = new TranslateAnimation(0, -200, 0, 150);
                                            animation2.setDuration(500);
                                            enemy.startAnimation(animation2);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {

                                            fakeNeutre(dmgMe, dmgYou);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    }
                            );
                            ally.startAnimation(animation);

                            break;
                        case "rpt":
                            animation = new TranslateAnimation(0, 200, 0, -150);
                            animation.setDuration(500);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    Animation animation2 = new TranslateAnimation(0, -400, 0, 300);
                                    animation2.setDuration(500);
                                    animation2.setStartOffset(500);
                                    enemy.startAnimation(animation2);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    fakeDeffective(dmgMe);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ally.startAnimation(animation);

                            break;
                        case "spl":
                            animation = new TranslateAnimation(0, 400, 0, -300);
                            animation.setDuration(500);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    enemy.startAnimation(splAnim());
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                    fakeEffective(dmgYou);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ally.startAnimation(animation);
                            break;
                    }
                    break;
                case "rpt":
                    switch (actionOther) {
                        case "atk":
                            //anim riposte
                            animation = new TranslateAnimation(0, 400, 0, -300);
                            animation.setStartOffset(500);
                            animation.setDuration(500);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                                               @Override
                                                               public void onAnimationStart(Animation animation) {
                                                                   //anim atk
                                                                   Animation animation2 = new TranslateAnimation(0, -200, 0, 150);
                                                                   animation2.setDuration(500);
                                                                   enemy.startAnimation(animation2);
                                                               }

                                                               @Override
                                                               public void onAnimationEnd(Animation animation) {
                                                                   fakeEffective(dmgYou);
                                                               }

                                                               @Override
                                                               public void onAnimationRepeat(Animation animation) {

                                                               }
                                                           }

                            );
                            ally.startAnimation(animation);
                            break;
                        case "rpt":
                            fakeNeutre(dmgMe, dmgYou);
                            break;
                        case "spl":
                            s = splAnim();
                            s.setAnimationListener(
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            fakeDeffective(dmgMe);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    }
                            );
                            enemy.startAnimation(s);

                            break;
                    }

                    break;
                case "spl":
                    switch (actionOther) {
                        case "atk":
                            s = splAnim();
                            s.setAnimationListener(
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            Animation animation2 = new TranslateAnimation(0, -200, 0, 150);
                                            animation2.setDuration(500);
                                            enemy.startAnimation(animation2);
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            fakeDeffective(dmgMe);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    }
                            );
                            ally.startAnimation(s);

                            break;
                        case "rpt":
                            s = splAnim();
                            s.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    fakeEffective(dmgYou);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            ally.startAnimation(s);
                            break;
                        case "spl":
                            s = splAnim();
                            s.setAnimationListener(
                                    new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            enemy.startAnimation(splAnim());
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            fakeNeutre(dmgMe, dmgYou);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    }
                            );
                            ally.startAnimation(s);

                            break;
                    }

                    break;
            }

    }

    private AnimationSet splAnim(){
        AnimationSet s = new AnimationSet(false);
        Animation animation = new TranslateAnimation(0, 0,0, -100);
        animation.setDuration(250);
        s.addAnimation(animation);
        Animation animation2 = new TranslateAnimation(0, 0,0, 200);
        animation2.setDuration(250);
        animation2.setStartOffset(250);
        s.addAnimation(animation2);
        return s;
    }

    private void effective(int atka, int atke, int defe, String actionMe) {
        int vale = (atka + atke - defe > 0 ? atka + atke - defe : 0);
        int rese = hpe.getProgress() - vale;
        dmge.setTextColor(Color.YELLOW);
        // Damage animation
        dmg(dmge, vale, " !!!");

        Map<String, String > dmg = new HashMap<>();
        dmg.put("pony", "dmg");
        dmg.put("actMe", actionMe);
        dmg.put("actYou", otherAction);
        dmg.put("dmgYou", vale+"");
        dmg.put("dmgMe", 0+"");
        sendMessage(dmg);

        if (rese > 0) {
            hpe.setProgress(rese);
            enableButton();
        }
        else {
            hpe.setProgress(0);
            finish();
        }
    }

    private void deffective(int atka, int defa, int atke, String actionMe) {
        int vala = (atke + atka - defa > 0 ? atke + atka - defa : 0);
        int resa = hpa.getProgress() - vala;
        dmga.setTextColor(Color.YELLOW);
        // Damage animation
        dmg(dmga, vala, " !!!");

        Map<String, String > dmg = new HashMap<>();
        dmg.put("pony", "dmg");
        dmg.put("actMe", actionMe);
        dmg.put("actYou", otherAction);
        dmg.put("dmgMe", vala+"");
        dmg.put("dmgYou", 0+"");
        sendMessage(dmg);

        if (resa > 0) {
            hpa.setProgress(resa);
            enableButton();
        }
        else {
            hpa.setProgress(0);
            finish();
        }
    }

    private void neutre(int atka, int defa, int atke, int defe, String actionMe) {

        int vala = (atke - defa > 0 ? atke - defa : 0);
        int vale = (atka - defe > 0 ? atka - defe : 0);
        int resa = hpa.getProgress() - vala;
        int rese = hpe.getProgress() - vale;

        dmge.setTextColor(Color.rgb(255, 153, 153));
        dmga.setTextColor(Color.rgb(255, 153, 153));
        dmg(dmga, vala, " !");
        dmg(dmge, vale, " !");

        Map<String, String > dmg = new HashMap<>();
        dmg.put("pony", "dmg");
        dmg.put("actMe", actionMe);
        dmg.put("actYou", otherAction);
        dmg.put("dmgMe", vala+"");
        dmg.put("dmgYou", vale+"");
        sendMessage(dmg);

        if (resa > 0)
            hpa.setProgress(resa);
        else
            hpa.setProgress(0);
        if (rese > 0)
            hpe.setProgress(rese);
        else
            hpe.setProgress(0);
        if (resa < 0 || rese < 0) {
            finish();
        }
        else
            enableButton();
    }


    private void fakeEffective(int vale) {
        int rese = hpe.getProgress() - vale;
        dmge.setTextColor(Color.YELLOW);
        // Damage animation
        dmg(dmge, vale, " !!!");

        if (rese > 0) {
            hpe.setProgress(rese);
            enableButton();
        }
        else {
            hpe.setProgress(0);
            finish();
        }
    }

    private void fakeDeffective(int vala) {
        int resa = hpa.getProgress() - vala;
        dmga.setTextColor(Color.YELLOW);
        // Damage animation
        dmg(dmga, vala, " !!!");

        if (resa > 0) {
            hpa.setProgress(resa);
            enableButton();
        }
        else {
            hpa.setProgress(0);
            finish();
        }
    }

    private void fakeNeutre(int vala, int vale) {

        int resa = hpa.getProgress() - vala;
        int rese = hpe.getProgress() - vale;

        dmge.setTextColor(Color.rgb(255, 153, 153));
        dmga.setTextColor(Color.rgb(255, 153, 153));
        dmg(dmga, vala, " !");
        dmg(dmge, vale, " !");

        if (resa > 0)
            hpa.setProgress(resa);
        else
            hpa.setProgress(0);
        if (rese > 0)
            hpe.setProgress(rese);
        else
            hpe.setProgress(0);
        if (resa < 0 || rese < 0) {
            finish();
        }
        else
            enableButton();
    }



    private void disableButton(){
        atk.setEnabled(false);
        riposte.setEnabled(false);
        special.setEnabled(false);
        potion.setEnabled(false);
    }

    private void enableButton(){
        otherAction = "";
        atk.setEnabled(true);
        riposte.setEnabled(true);
        special.setEnabled(true);
        potion.setEnabled(true);
    }

    private void dmg(TextView txt, int val, String bonus){
        txt.setText("- " + val + bonus);
        Animation fade_in = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        Animation fade_out = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

        AnimationSet s = new AnimationSet(false);
        s.addAnimation(fade_in);
        fade_out.setStartOffset(fade_in.getDuration());
        s.addAnimation(fade_out);
        s.setFillAfter(true);
        txt.startAnimation(s);
    }


    private void finish() {
        if(hpa.getProgress() > 0 ){
            mine.setLevel(mine.getLevel()+1);
            ((HelperActivity) getActivity()).getHelper().updateBattler(mine);

            Fragment fragment = ResultFragment.newInstance(""+mine.getLevel(), ""+(mine.getType()+1));
            ((FightActivity) getActivity()).changeFragment(fragment);
        }
        else{
            Fragment fragment = ResultFragment.newInstance("", ""+(mine.getType()+1));
            ((FightActivity) getActivity()).changeFragment(fragment);
        }

    }

    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {

        //initialise the secure button
        btnSec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            }
        });

        //initialise the insecure button

        btnInsec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);

            }
        });

        //initialise the  discoverable button

        btnDisco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
            }
        });

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(getActivity(), mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    /**
     * Makes this device discoverable for 300 seconds (5 minutes).
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(Map<String, String> message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.size() > 0) {
            JSONObject jsonObject = new JSONObject(message);
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = jsonObject.toString().getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }


    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {
        if (null == getActivity()) {
            return;
        }
        final ActionBar actionBar = getActivity().getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    private void sendMinion(){
        Map<String, String> minion = new HashMap<>();
        minion.put("pony", "mob");
        minion.put("hp", mine.getHp() + "");
        minion.put("type", mine.getType() + "");
        minion.put("atk", mine.getAtk() + "");
        minion.put("def", mine.getDef() + "");
        minion.put("lvl", mine.getLevel() + "");
        sendMessage(minion);
    }

    private void readMessage(String msg){
        try {
            JSONObject obj = new JSONObject(msg);
            if(obj.getString("pony").equals("action")){
                otherAction = obj.getString("action");

            }
            else if (obj.getString("pony").equals("dmg")){
                String actionYou = obj.getString("actMe");
                String actionMe = obj.getString("actYou");
                int dmgMe = Integer.parseInt(obj.getString("dmgYou"));
                int dmgYou = Integer.parseInt(obj.getString("dmgMe"));
                fakeFight(actionMe, actionYou, dmgMe, dmgYou);
            }
            else if (obj.getString("pony").equals("mob")){
                int atk = Integer.parseInt(obj.getString("atk"));
                int hp = Integer.parseInt(obj.getString("hp"));
                int type = Integer.parseInt(obj.getString("type"));
                int def = Integer.parseInt(obj.getString("def"));
                int lvl = Integer.parseInt(obj.getString("lvl"));
                bad = new Battler(hp, atk, def, type);
                bad.setLevel(lvl);
                int id2 = getResources().getIdentifier("sprite_" + (type + 1), "drawable", "com.mbds.barcodebattlermt");
                enemy.setImageResource(id2);
                hpe.setMax(bad.getLvlHp());
                hpe.setProgress(bad.getLvlHp());
                rl.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {
        if (null == getActivity()) {
            return;
        }
        final ActionBar actionBar = getActivity().getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            sendMinion();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    readMessage(readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != getActivity()) {
                        Toast.makeText(getActivity(), "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != getActivity()) {
                        Toast.makeText(getActivity(), msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }
}
