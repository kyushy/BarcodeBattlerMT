package com.mbds.barcodebattlermt.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.activities.FightActivity;
import com.mbds.barcodebattlermt.activities.HelperActivity;
import com.mbds.barcodebattlermt.controler.Controler;
import com.mbds.barcodebattlermt.model.Battler;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FightFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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


    private boolean finish = false;


    private Battler mine;
    private Battler bad;


    private OnFragmentInteractionListener mListener;

    public FightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FightFragment newInstance(String param1, String param2) {
        FightFragment fragment = new FightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fight, container, false);
    }

    public Battler randomMob(int lvl){
        Random r = new Random();
        int i = r.nextInt(10000000 - 1000000) + 1000000;
        Battler b =(Battler)Controler.generate(i+"0");
                b.setLevel(lvl);
        return b;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        bad = randomMob(mine.getLevel());

        int id = getResources().getIdentifier("sprite_" + (mine.getType() + 1), "drawable", "com.mbds.barcodebattlermt");
        ally.setImageResource(id);
        int id2 = getResources().getIdentifier("sprite_" + (bad.getType() + 1), "drawable", "com.mbds.barcodebattlermt");
        enemy.setImageResource(id2);
        hpa.setMax(mine.getLvlHp());
        hpe.setMax(bad.getLvlHp());
        hpa.setProgress(mine.getLvlHp());
        hpe.setProgress(bad.getLvlHp());

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

    public void fight(String action) {
        String actionBot = decision();
        final int atka = new Random().nextInt(mine.getLvlAtk()+1);
        final int defa = new Random().nextInt(mine.getLvlDef()+1);
        final int atke = new Random().nextInt(bad.getLvlAtk()+1);
        final int defe = new Random().nextInt(bad.getLvlDef()+1);
        Animation animation;
        Animation animation2;
        AnimationSet s;
        disableButton();
        switch (action) {
            case "atk":
                switch (actionBot) {
                    case "atk":
                        animation = new TranslateAnimation(0, 200,0, -150);
                        animation.setDuration(500);
                        animation.setAnimationListener(
                                new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        Animation animation2 = new TranslateAnimation(0, -200,0, 150);
                                        animation2.setDuration(500);
                                        enemy.startAnimation(animation2);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        neutre(atka, defa, atke, defe);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                }
                        );
                        ally.startAnimation(animation);

                        break;
                    case "rpt":
                        animation = new TranslateAnimation(0, 200,0, -150);
                        animation.setDuration(500);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                Animation animation2 = new TranslateAnimation(0, -400,0, 300);
                                animation2.setDuration(500);
                                animation2.setStartOffset(500);
                                enemy.startAnimation(animation2);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                deffective(atka, defa, atke);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        ally.startAnimation(animation);

                        break;
                    case "spl":
                        animation = new TranslateAnimation(0, 400,0, -300);
                        animation.setDuration(500);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                enemy.startAnimation(splAnim());
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                                effective(atka, atke, defe);
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
                switch (actionBot) {
                    case "atk":
                        //anim riposte
                        animation = new TranslateAnimation(0, 400,0, -300);
                        animation.setStartOffset(500);
                        animation.setDuration(500);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                                                           @Override
                                                           public void onAnimationStart(Animation animation) {
                                                               //anim atk
                                                               Animation animation2 = new TranslateAnimation(0, -200,0, 150);
                                                               animation2.setDuration(500);
                                                               enemy.startAnimation(animation2);
                                                           }

                                                           @Override
                                                           public void onAnimationEnd(Animation animation) {
                                                               effective(atka, atke, defe);
                                                           }

                                                           @Override
                                                           public void onAnimationRepeat(Animation animation) {

                                                           }
                                                       }

                        );
                        ally.startAnimation(animation);
                        break;
                    case "rpt":
                        neutre(0, defa, 0, defe);
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
                                        deffective(atka, defa, atke);
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
                switch (actionBot) {
                    case "atk":
                        s = splAnim();
                        s.setAnimationListener(
                                new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        Animation animation2 = new TranslateAnimation(0, -200,0, 150);
                                        animation2.setDuration(500);
                                        enemy.startAnimation(animation2);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        deffective(atka, defa, atke);
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
                                effective(atka, atke, defe);
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
                                        neutre(atka, defa, atke, defe);
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

    private void effective(int atka, int atke, int defe) {
        int vale = (atka + atke - defe > 0 ? atka + atke - defe : 0);
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

    private void deffective(int atka, int defa, int atke) {
        int vala = (atke + atka - defa > 0 ? atke + atka - defa : 0);
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

    private void disableButton(){
        atk.setEnabled(false);
        riposte.setEnabled(false);
        special.setEnabled(false);
        potion.setEnabled(false);
    }

    private void enableButton(){
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

    private void neutre(int atka, int defa, int atke, int defe) {

        int vala = (atke - defa > 0 ? atke - defa : 0);
        int vale = (atka - defe > 0 ? atka - defe : 0);
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

    private String decision() {
        Random r = new Random();
        int i = r.nextInt(3);
        String res;
        if (i < 1) {
            res = "atk";
        } else if (i < 2) {
            res = "rpt";
        } else {
            res = "spl";
        }
        return res;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
