package com.mbds.barcodebattlermt.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.activities.FightActivity;
import com.mbds.barcodebattlermt.activities.GameActivity;
import com.mbds.barcodebattlermt.activities.HelperActivity;
import com.mbds.barcodebattlermt.controler.BattlerAdapter;
import com.mbds.barcodebattlermt.model.Battler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BattlerDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BattlerDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BattlerDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Battler battler;

    private TextView hpView;
    private TextView atkView;
    private TextView defView;
    private TextView lvlView;
    private TextView lvlCont;

    private TextView hpItemView;
    private TextView atkItemView;
    private TextView defItemView;

    private RelativeLayout itemHp_slot;
    private RelativeLayout itemAtk_slot;
    private RelativeLayout itemDef_slot;

    private OnFragmentInteractionListener mListener;

    public BattlerDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BattlerDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BattlerDetailFragment newInstance(String param1, String param2) {
        BattlerDetailFragment fragment = new BattlerDetailFragment();
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

        View view = inflater.inflate(R.layout.fragment_battler_detail, container, false);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        battler = ((HelperActivity) getActivity()).getHelper().getBattler(mParam1);

        itemHp_slot = (RelativeLayout) view.findViewById(R.id.item_hp_slot);
        itemAtk_slot = (RelativeLayout) view.findViewById(R.id.item_atk_slot);
        itemDef_slot = (RelativeLayout) view.findViewById(R.id.item_def_slot);

        hpView = (TextView) view.findViewById(R.id.hp_battler_d);
        atkView = (TextView) view.findViewById(R.id.atk_battler_d);
        defView = (TextView) view.findViewById(R.id.def_battler_d);
        lvlCont = (TextView) view.findViewById(R.id.lvl_row_d);
        lvlView = (TextView) view.findViewById(R.id.lvl_battler_row_d);

        hpView.setText(String.valueOf(battler.getHp()));
        atkView.setText(String.valueOf(battler.getAtk()));
        defView.setText(String.valueOf(battler.getDef()));
        lvlView.setText(String.valueOf(battler.getLevel()));

        lvlView.setVisibility(View.VISIBLE);
        lvlCont.setVisibility(View.VISIBLE);

        if(battler.getHpItem() != null){
            hpItemView = (TextView) view.findViewById(R.id.hp_battler_i1);
            hpItemView.setText(String.valueOf(battler.getHpItem().getHp()));
        }

        if(battler.getAtkItem() != null){
            atkItemView = (TextView) view.findViewById(R.id.atk_battler_i2);
            atkItemView.setText(String.valueOf(battler.getHpItem().getHp()));
        }

        if(battler.getDefItem() != null){
            defItemView = (TextView) view.findViewById(R.id.def_battler_i3);
            defItemView.setText(String.valueOf(battler.getHpItem().getHp()));
        }

        itemHp_slot.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = GearListFragment.newInstance(""+battler.getId(), "HP");
                ((GameActivity) getActivity()).changeFragment(fragment);
            }
        });

        itemAtk_slot.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = GearListFragment.newInstance(""+battler.getId(), "ATK");
                ((GameActivity) getActivity()).changeFragment(fragment);
            }
        });

        itemDef_slot.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = GearListFragment.newInstance(""+battler.getId(), "DEF");
                ((GameActivity) getActivity()).changeFragment(fragment);
            }
        });

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
