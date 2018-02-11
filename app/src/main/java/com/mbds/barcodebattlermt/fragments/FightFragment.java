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
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.activities.HelperActivity;
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

        mine = ((HelperActivity) getActivity()).getHelper().getBattler(1);
        bad = ((HelperActivity) getActivity()).getHelper().getBattler(1);
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
        int atka = new Random().nextInt(mine.getLvlAtk());
        int defa = new Random().nextInt(mine.getLvlDef());
        int atke = new Random().nextInt(bad.getLvlAtk());
        int defe = new Random().nextInt(bad.getLvlDef());
        switch (action) {
            case "atk":
                switch (actionBot) {
                    case "atk":
                        neutre(atka, defa, atke, defe);
                        break;
                    case "rpt":
                        deffective(atka, defa, atke);
                        break;
                    case "spl":
                        effective(atka, atke, defe);
                        break;
                }
                break;
            case "rpt":
                switch (actionBot) {
                    case "atk":
                        effective(atka, atke, defe);
                        break;
                    case "rpt":
                        neutre(atka, defa, atke, defe);
                        break;
                    case "spl":
                        deffective(atka, defa, atke);
                        break;
                }

                break;
            case "spl":
                switch (actionBot) {
                    case "atk":
                        deffective(atka, defa, atke);
                        break;
                    case "rpt":
                        effective(atka, atke, defe);
                        break;
                    case "spl":
                        neutre(atka, defa, atke, defe);
                        break;
                }

                break;
        }
    }

    private void effective(int atka, int atke, int defe) {
        Animation animation = new TranslateAnimation(0, 400,0, -300);
        animation.setDuration(500);
        ally.startAnimation(animation);
        int rese = hpe.getProgress() - (atka + atke - defe > 0 ? atka + atke - defe : 0);

        if (rese > 0)
            hpe.setProgress(rese);
        else {
            hpe.setProgress(0);
            finish();
        }
    }

    private void deffective(int atka, int defa, int atke) {
        Animation animation = new TranslateAnimation(0, -400,0, 300);
        animation.setDuration(500);
        enemy.startAnimation(animation);
        int resa = hpa.getProgress() - (atke + atka - defa > 0 ? atke + atka - defa : 0);

        if (resa > 0)
            hpa.setProgress(resa);
        else {
            hpa.setProgress(0);
            finish();
        }
    }

    private void neutre(int atka, int defa, int atke, int defe) {
        Animation animation = new TranslateAnimation(0, 200,0, -150);
        animation.setDuration(500);
        ally.startAnimation(animation);
        Animation animation2 = new TranslateAnimation(0, -200,0, 150);
        animation2.setDuration(500);
        enemy.startAnimation(animation2);
        int resa = hpa.getProgress() - (atke - defa > 0 ? atke - defa : 0);
        int rese = hpe.getProgress() - (atka - defe > 0 ? atka - defe : 0);
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
    }

    private void finish() {
        System.out.println("hpa " + hpa.getProgress());
        System.out.println("hpe " + hpe.getProgress());

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
