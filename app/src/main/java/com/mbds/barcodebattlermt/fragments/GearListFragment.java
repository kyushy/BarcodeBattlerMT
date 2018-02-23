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
import android.widget.ListView;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.activities.GameActivity;
import com.mbds.barcodebattlermt.activities.HelperActivity;
import com.mbds.barcodebattlermt.bluetooth.BluetoothActivity;
import com.mbds.barcodebattlermt.bluetooth.BluetoothChatFragment;
import com.mbds.barcodebattlermt.controler.BattlerAdapter;
import com.mbds.barcodebattlermt.model.AtkItem;
import com.mbds.barcodebattlermt.model.Battler;
import com.mbds.barcodebattlermt.model.DefItem;
import com.mbds.barcodebattlermt.model.GenFromBarCode;
import com.mbds.barcodebattlermt.model.HpItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GearListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GearListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GearListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView gearsListView;
    private List<GenFromBarCode> gears;
    private Battler battler;

    private OnFragmentInteractionListener mListener;

    public GearListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GearListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GearListFragment newInstance(String param1, String param2) {
        GearListFragment fragment = new GearListFragment();
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
        View view = inflater.inflate(R.layout.fragment_gear_list, container, false);
        gearsListView = (ListView) view.findViewById(R.id.gears_list);

        gears = new ArrayList<>();
        if(mParam2 == "")
            gears = ((GameActivity) getActivity()).getHelper().getGears();
        if(mParam2 == "HP")
            gears = ((GameActivity) getActivity()).getHelper().getHpItems();
        if(mParam2 == "ATK")
            gears = ((GameActivity) getActivity()).getHelper().getAtkItems();
        if(mParam2 == "DEF")
            gears = ((GameActivity) getActivity()).getHelper().getDefItems();

        BattlerAdapter adapter = new BattlerAdapter(getActivity(), gears, getResources());
        gearsListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(mParam1 != "")
            battler = ((HelperActivity) getActivity()).getHelper().getBattler(mParam1);

        gearsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if(mParam2 == "HP") {
                    HpItem entry = (HpItem) parent.getItemAtPosition(position);
                    battler.setHpItem(entry);
                    ((HelperActivity) getActivity()).getHelper().updateBattler(battler);
                    Fragment fragment = BattlerDetailFragment.newInstance(""+battler.getId(), "");
                    ((GameActivity) getActivity()).changeFragment(fragment);
                }

                if(mParam2 == "ATK") {
                    AtkItem entry = (AtkItem) parent.getItemAtPosition(position);
                    battler.setAtkItem(entry);
                    ((HelperActivity) getActivity()).getHelper().updateBattler(battler);
                    Fragment fragment = BattlerDetailFragment.newInstance(""+battler.getId(), "");
                    ((GameActivity) getActivity()).changeFragment(fragment);
                }

                if(mParam2 == "DEF") {
                    DefItem entry = (DefItem) parent.getItemAtPosition(position);
                    battler.setDefItem(entry);
                    ((HelperActivity) getActivity()).getHelper().updateBattler(battler);
                    Fragment fragment = BattlerDetailFragment.newInstance(""+battler.getId(), "");
                    ((GameActivity) getActivity()).changeFragment(fragment);
                }
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
