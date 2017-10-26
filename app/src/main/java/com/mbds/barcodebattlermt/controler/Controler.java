package com.mbds.barcodebattlermt.controler;

import com.mbds.barcodebattlermt.model.Battler;
import com.mbds.barcodebattlermt.model.GenFromBarCode;

import java.security.InvalidParameterException;

/**
 * Created by JuIngong on 26/10/2017.
 */

public class Controler {

    public static GenFromBarCode generate(String barcode) {
        int type = Character.getNumericValue(barcode.charAt(0));
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
            case 9:
                int hpI = 100 * (Character.getNumericValue(barcode.charAt(5)) * 10
                        + Character.getNumericValue(barcode.charAt(4)));
                hpI = Character.getNumericValue(barcode.charAt(6)) >= 7 ? hpI + 1000 : hpI;
                return new Battler(hpI, 0, 0, type);
            // new hpItem A B C D E F G H 7
            case 5:
            case 6:
                int atkI = 100 * ((Character.getNumericValue(barcode.charAt(5))
                        + Character.getNumericValue(barcode.charAt(4)) + 1) % 10);
                atkI = Character.getNumericValue(barcode.charAt(6)) >= 7 ? atkI + 1000 : atkI;
                return new Battler(0, atkI, 0, type);
            //new atkItem
            case 7:
            case 8:
                int defI = 100 * ((Character.getNumericValue(barcode.charAt(5))
                        + Character.getNumericValue(barcode.charAt(4)) + 3) % 10);
                defI = Character.getNumericValue(barcode.charAt(6)) >= 7 ? defI + 1000 : defI;
                return new Battler(0, 0, defI, type);
            // new DefItem
        }
        throw new InvalidParameterException("Barcode incorrect");
    }
}
