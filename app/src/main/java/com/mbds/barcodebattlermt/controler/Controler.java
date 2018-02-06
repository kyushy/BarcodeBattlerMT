package com.mbds.barcodebattlermt.controler;

import com.mbds.barcodebattlermt.model.DefItem;
import com.mbds.barcodebattlermt.model.Battler;
import com.mbds.barcodebattlermt.model.GenFromBarCode;
import com.mbds.barcodebattlermt.model.HpItem;
import com.mbds.barcodebattlermt.model.Potion;
import com.mbds.barcodebattlermt.model.AtkItem;

import java.security.InvalidParameterException;

/**
 * Created by JuIngong on 26/10/2017.
 */

public class Controler {

    public static GenFromBarCode generate(String barcode) {
        int type = Character.getNumericValue(barcode.charAt(0));
        //int type = Character.getNumericValue(barcode.charAt(7));
        switch (Character.getNumericValue(barcode.charAt(7))) {
            case 0:
            case 2:
            case 3:
            case 4:
                int hp = 100 * (10 * Character.getNumericValue(barcode.charAt(6)) / 2
                        + Character.getNumericValue(barcode.charAt(5)));
                int atk = 100 * ((Character.getNumericValue(barcode.charAt(4))
                        + Character.getNumericValue(barcode.charAt(3))) %
                        (Character.getNumericValue(barcode.charAt(2))
                                + 3));
                atk = atk < 300 ? atk + 1000 : atk;
                int def = 100 * Character.getNumericValue(barcode.charAt(1));
                def = def < 300 ? def + 1000 : def;
                return new Battler(hp, atk, def, type);
            case 1:
                int hpP = 0;
                int atkP = 0;
                int defP = 0;
                if (type < 3) {
                    hpP = 100 * (Character.getNumericValue(barcode.charAt(5)) * 10
                            + Character.getNumericValue(barcode.charAt(4)));
                    hpP = Character.getNumericValue(barcode.charAt(6)) >= 7 ? hpP + 1000 : hpP;
                } else if (type < 6) {
                    atkP = 100 * ((Character.getNumericValue(barcode.charAt(5))
                            + Character.getNumericValue(barcode.charAt(4)) + 1) % 10);
                    atkP = Character.getNumericValue(barcode.charAt(6)) >= 7 ? atkP + 1000 : atkP;
                } else if (type < 9) {
                    defP = 100 * ((Character.getNumericValue(barcode.charAt(5))
                            + Character.getNumericValue(barcode.charAt(4)) + 3) % 10);
                    defP = Character.getNumericValue(barcode.charAt(6)) >= 7 ? defP + 1000 : defP;
                } else {
                    hpP = 100 * (Character.getNumericValue(barcode.charAt(5)) * 10
                            + Character.getNumericValue(barcode.charAt(4)));
                    hpP = Character.getNumericValue(barcode.charAt(6)) >= 7 ? hpP + 1000 : hpP;
                    atkP = 100 * ((Character.getNumericValue(barcode.charAt(5))
                            + Character.getNumericValue(barcode.charAt(4)) + 1) % 10);
                    atkP = Character.getNumericValue(barcode.charAt(6)) >= 7 ? atkP + 1000 : atkP;
                    defP = 100 * ((Character.getNumericValue(barcode.charAt(5))
                            + Character.getNumericValue(barcode.charAt(4)) + 3) % 10);
                    defP = Character.getNumericValue(barcode.charAt(6)) >= 7 ? defP + 1000 : defP;
                }
                return new Potion(hpP, atkP, defP, type);
            // new potionItem
            case 9:
                int hpI = 100 * (Character.getNumericValue(barcode.charAt(5)) * 10
                        + Character.getNumericValue(barcode.charAt(4)));
                hpI = Character.getNumericValue(barcode.charAt(6)) >= 7 ? hpI + 1000 : hpI;
                return new HpItem(hpI, type);
            // new hpItem
            case 5:
            case 6:
                int atkI = 100 * ((Character.getNumericValue(barcode.charAt(5))
                        + Character.getNumericValue(barcode.charAt(4)) + 1) % 10);
                atkI = Character.getNumericValue(barcode.charAt(6)) >= 7 ? atkI + 1000 : atkI;
                return new AtkItem(atkI, type);
            //new atkItem
            case 7:
            case 8:
                int defI = 100 * ((Character.getNumericValue(barcode.charAt(5))
                        + Character.getNumericValue(barcode.charAt(4)) + 3) % 10);
                defI = Character.getNumericValue(barcode.charAt(6)) >= 7 ? defI + 1000 : defI;
                return new DefItem(defI, type);
            // new DefItem
        }
        throw new InvalidParameterException("Barcode incorrect");
    }
}
