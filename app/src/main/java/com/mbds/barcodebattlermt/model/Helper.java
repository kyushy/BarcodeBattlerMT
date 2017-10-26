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

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BATTLER = "CREATE TABLE Battlers ( " +
                "Id     INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Hp     INTEGER NOT NULL," +
                "Atk    INTEGER NOT NULL," +
                "Def    INTEGER NOT NULL," +
                "Type   INTEGER NOT NULL," +
                "Level  INTEGER NOT NULL," +
                ");";
        db.execSQL(CREATE_BATTLER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Battlers;");
        onCreate(db);
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
        db.close();
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
        db.close();


    }

    public List<Battler> getBattlers() {
        List<Battler> battlers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //db.query ou rawQuery -> renvoie un Cursor, sorte de vue de résultats de la base de donnée
        Cursor cursor = db.rawQuery("SELECT * FROM Battlers;", null);

        //Parcours des résultats  :
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Battler b = new Battler();
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
}
