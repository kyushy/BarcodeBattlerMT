package com.mbds.barcodebattlermt.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Helper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Battlers";

    private SQLiteDatabase db;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BATTLER = "CREATE TABLE Battlers ( " +
                "Id     INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Hp     INTEGER NOT NULL," +
                "Atk    INTEGER NOT NULL," +
                "Def    INTEGER NOT NULL," +
                "Type   INTEGER NOT NULL," +
                "Level  INTEGER NOT NULL " +
                ");";
        db.execSQL(CREATE_BATTLER);

        String CREATE_HPITEM = "CREATE TABLE HpItems ( " +
                "Id     INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Hp    INTEGER NOT NULL," +
                "Type   INTEGER NOT NULL" +
                ");";
        db.execSQL(CREATE_HPITEM);

        String CREATE_ATKITEM = "CREATE TABLE AtkItems ( " +
                "Id     INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Atk    INTEGER NOT NULL," +
                "Type   INTEGER NOT NULL" +
                ");";
        db.execSQL(CREATE_ATKITEM);

        String CREATE_DEFITEM = "CREATE TABLE DefItems ( " +
                "Id     INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Def    INTEGER NOT NULL," +
                "Type   INTEGER NOT NULL" +
                ");";
        db.execSQL(CREATE_DEFITEM);

        String CREATE_POTION = "CREATE TABLE Potions ( " +
                "Id     INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Hp     INTEGER NOT NULL," +
                "Atk    INTEGER NOT NULL," +
                "Def    INTEGER NOT NULL," +
                "Type   INTEGER NOT NULL" +
                ");";
        db.execSQL(CREATE_POTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Battlers;");
        db.execSQL("DROP TABLE HpItems;");
        db.execSQL("DROP TABLE AtkItems;");
        db.execSQL("DROP TABLE DefItems;");
        db.execSQL("DROP TABLE Potions;");
        onCreate(db);
    }

    public void clearDb(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Battlers", null, null);
        db.delete("HpItems", null, null);
        db.delete("AtkItems", null, null);
        db.delete("DefItems", null, null);
        db.delete("Potions", null, null);
    }

    public void addBattler(Battler b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Hp", b.getHp());
        values.put("Atk", b.getAtk());
        values.put("Def", b.getDef());
        values.put("Type", b.getType());
        values.put("Level", b.getLevel());
        db.insert("Battlers", null, values);
        //db.close();
    }

    public void UpdateBattler(Battler b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Hp", b.getHp());
        values.put("Atk", b.getAtk());
        values.put("Def", b.getDef());
        values.put("Type", b.getType());
        values.put("Level", b.getLevel());
        db.update("Battlers", values, "Id = " + b.getId(), null);
        //db.close();


    }

    public List<GenFromBarCode> getBattlers() {
        List<GenFromBarCode> battlers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //db.query ou rawQuery -> renvoie un Cursor, sorte de vue de résultats de la base de donnée
        Cursor cursor = db.rawQuery("SELECT * FROM Battlers;", null);

        //Parcours des résultats  :
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Battler b = new Battler();
                b.setId(cursor.getInt(0));
                b.setHp(cursor.getInt(1));
                b.setAtk(cursor.getInt(2));
                b.setDef(cursor.getInt(3));
                b.setType(cursor.getInt(4));
                b.setLevel(cursor.getInt(5));
                battlers.add(b);
                if (!cursor.isLast())
                    cursor.moveToNext();
            }

        }

        return battlers;
    }

    public List<HpItem> getHpItems() {
        List<HpItem> hpItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //db.query ou rawQuery -> renvoie un Cursor, sorte de vue de résultats de la base de donnée
        Cursor cursor = db.rawQuery("SELECT * FROM HpItems;", null);

        //Parcours des résultats  :
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                HpItem item = new HpItem();
                item.setId(cursor.getInt(0));
                item.setHp(cursor.getInt(1));
                item.setType(cursor.getInt(2));
                hpItems.add(item);
                if (!cursor.isLast())
                    cursor.moveToNext();
            }

        }

        return hpItems;
    }

    public List<AtkItem> getAtkItems() {
        List<AtkItem> atkItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //db.query ou rawQuery -> renvoie un Cursor, sorte de vue de résultats de la base de donnée
        Cursor cursor = db.rawQuery("SELECT * FROM AtkItems;", null);

        //Parcours des résultats  :
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                AtkItem item = new AtkItem();
                item.setId(cursor.getInt(0));
                item.setAtk(cursor.getInt(1));
                item.setType(cursor.getInt(2));
                atkItems.add(item);
                if (!cursor.isLast())
                    cursor.moveToNext();
            }

        }

        return atkItems;
    }

    public List<DefItem> getDefItems() {
        List<DefItem> defItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //db.query ou rawQuery -> renvoie un Cursor, sorte de vue de résultats de la base de donnée
        Cursor cursor = db.rawQuery("SELECT * FROM DefItems;", null);

        //Parcours des résultats  :
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                DefItem item = new DefItem();
                item.setId(cursor.getInt(0));
                item.setDef(cursor.getInt(1));
                item.setType(cursor.getInt(2));
                defItems.add(item);
                if (!cursor.isLast())
                    cursor.moveToNext();
            }

        }

        return defItems;
    }

    public List<Potion> getPotions() {
        List<Potion> potions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //db.query ou rawQuery -> renvoie un Cursor, sorte de vue de résultats de la base de donnée
        Cursor cursor = db.rawQuery("SELECT * FROM Potions;", null);

        //Parcours des résultats  :
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Potion item = new Potion();
                item.setId(cursor.getInt(0));
                item.setHp(cursor.getInt(1));
                item.setAtk(cursor.getInt(2));
                item.setDef(cursor.getInt(3));
                item.setType(cursor.getInt(4));
                potions.add(item);
                if (!cursor.isLast())
                    cursor.moveToNext();
            }

        }

        return potions;
    }

    public List<GenFromBarCode> getGears() {
        List<GenFromBarCode> gears = new ArrayList<>();

        gears.addAll(this.getHpItems());
        gears.addAll(this.getAtkItems());
        gears.addAll(this.getDefItems());
        gears.addAll(this.getPotions());

        return gears;
    }
}
