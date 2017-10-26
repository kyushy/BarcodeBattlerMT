package com.mbds.barcodebattlermt.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mbds.barcodebattlermt.R;

import java.util.List;

/**
 * Created by Fred on 26/10/2017.
 */

public class BattlerAdapter extends ArrayAdapter<Battler> {

    public BattlerAdapter(Context context, List<Battler> battlers) {
        super(context, 0, battlers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_battler, parent, false);
        }

        BattlerViewHolder viewHolder = (BattlerViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new BattlerViewHolder();
            viewHolder.id = (TextView) convertView.findViewById(R.id.id_battler);
            viewHolder.hp = (TextView) convertView.findViewById(R.id.hp_battler);
            viewHolder.atk = (TextView) convertView.findViewById(R.id.atk_battler);
            viewHolder.def = (TextView) convertView.findViewById(R.id.def_battler);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Battler> battlers
        Battler battler = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.id.setText(battler.getId()+"");
        viewHolder.hp.setText(battler.getHp()+"");
        viewHolder.atk.setText(battler.getAtk()+"");
        viewHolder.def.setText(battler.getDef()+"");

        return convertView;
    }

    private class BattlerViewHolder{
        public TextView id;
        public TextView hp;
        public TextView atk;
        public TextView def;
    }
}


