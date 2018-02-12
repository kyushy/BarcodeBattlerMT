package com.mbds.barcodebattlermt.controler;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbds.barcodebattlermt.R;
import com.mbds.barcodebattlermt.model.AtkItem;
import com.mbds.barcodebattlermt.model.Battler;
import com.mbds.barcodebattlermt.model.DefItem;
import com.mbds.barcodebattlermt.model.GenFromBarCode;
import com.mbds.barcodebattlermt.model.HpItem;

import java.util.List;

/**
 * Created by Fred on 26/10/2017.
 */

public class BattlerAdapter extends ArrayAdapter<GenFromBarCode> {

    private Resources r;

    public BattlerAdapter(Context context, List<GenFromBarCode> battlers, Resources r) {
        super(context, 0, battlers);
        this.r = r;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_battler, parent, false);
        }

        BattlerViewHolder viewHolder = (BattlerViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new BattlerViewHolder();
            viewHolder.mob = (ImageView) convertView.findViewById(R.id.imageView3);
            viewHolder.hp = (TextView) convertView.findViewById(R.id.hp_battler);
            viewHolder.atk = (TextView) convertView.findViewById(R.id.atk_battler);
            viewHolder.def = (TextView) convertView.findViewById(R.id.def_battler);
            viewHolder.lvl = (TextView) convertView.findViewById(R.id.lvl_battler_row);
            viewHolder.lvlRow = (TextView) convertView.findViewById(R.id.lvl_row);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Battler> battlers
        GenFromBarCode battler = getItem(position);

        if(battler instanceof Battler ){
            int id = r.getIdentifier("sprite_" + (battler.getType() + 1), "drawable", "com.mbds.barcodebattlermt");
            viewHolder.mob.setImageResource(id);
            viewHolder.lvl.setVisibility(View.VISIBLE);
            viewHolder.lvlRow.setVisibility(View.VISIBLE);
            viewHolder.hp.setText(((Battler) battler).getLvlHp()+"");
            viewHolder.lvl.setText(((Battler) battler).getLevel()+"");
            viewHolder.atk.setText(((Battler) battler).getLvlAtk()+"");
            viewHolder.def.setText(((Battler) battler).getLvlDef()+"");
        }
        else {
            int id;
            if(battler instanceof AtkItem)
                id = r.getIdentifier("atk_" + (battler.getType() + 1), "drawable", "com.mbds.barcodebattlermt");
            else if(battler instanceof DefItem)
                id = r.getIdentifier("def_" + (battler.getType() + 1), "drawable", "com.mbds.barcodebattlermt");
            else if(battler instanceof HpItem)
                id = r.getIdentifier("hp_" + (battler.getType() + 1), "drawable", "com.mbds.barcodebattlermt");
            else
                id = r.getIdentifier("potions_" + (battler.getType() + 1), "drawable", "com.mbds.barcodebattlermt");

            viewHolder.mob.setImageResource(id);
            //il ne reste plus qu'à remplir notre vue
            viewHolder.hp.setText(battler.getHp() + "");
            viewHolder.atk.setText(battler.getAtk() + "");
            viewHolder.def.setText(battler.getDef() + "");
        }

        return convertView;
    }

    private class BattlerViewHolder{
        public TextView lvl;
        public TextView lvlRow;
        public ImageView mob;
        public TextView hp;
        public TextView atk;
        public TextView def;
    }
}


