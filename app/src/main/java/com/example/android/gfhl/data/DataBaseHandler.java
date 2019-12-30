package com.example.android.gfhl.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.gfhl.models.Champion;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gfhl.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ChampionContract.ChampionEntry.TABLE_NAME + " (" +
                    ChampionContract.ChampionEntry._ID + " INTEGER PRIMARY KEY," +
                    ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_ICON + " TEXT," +
                    ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_NAME + " TEXT," +
                    ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_TITLE + " TEXT," +
                    ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_HP + " TEXT," +
                    ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_MANA + " TEXT," +
                    ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_ARMOR + " TEXT," +
                    ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_MR + " TEXT," +
                    ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_DMG + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ChampionContract.ChampionEntry.TABLE_NAME;

    private Context context;


    public DataBaseHandler( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public long addChammpion (Champion c){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_ICON, c.getIcon());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_IMAGE, c.getImage());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_NAME, c.getName());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_TITLE, c.getTitle());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_HP, c.getHp());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_MANA, c.getMana());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_ARMOR, c.getArmor());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_MR, c.getMr());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_DMG, c.getDmg());

        long id = db.insert(ChampionContract.ChampionEntry.TABLE_NAME,null, contentValues);
        db.close();
        return id;
    }

    public ArrayList<Champion> getAllChampions(){

        ArrayList<Champion> champions = new ArrayList<>();

        SQLiteDatabase db=getReadableDatabase();

        String[] projection = {
                ChampionContract.ChampionEntry._ID,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_ICON,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_IMAGE,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_NAME,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_TITLE,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_HP,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_MANA,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_ARMOR,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_MR,
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_DMG
        };


        Cursor cursor = db.query(
                ChampionContract.ChampionEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_NAME               // The sort order
        );

        if (cursor.moveToFirst()){

            do {
                long id= cursor.getLong(0);
                String icon = cursor.getString(1);
                String image = cursor.getString(2);
                String name= cursor.getString(3);
                String title= cursor.getString(4);
                String hp= cursor.getString(5);
                String mana = cursor.getString(6);
                String armor = cursor.getString(7);
                String mr = cursor.getString(8);
                String dmg = cursor.getString(9);

                Champion product=new Champion(id, icon, image, name, title, hp, mana, armor, mr, dmg);
                champions.add(product);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return champions;
    }

    public int updateChampion(Champion c){

        SQLiteDatabase db= getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_ICON, c.getIcon());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_IMAGE, c.getImage());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_NAME, c.getName());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_TITLE, c.getTitle());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_HP, c.getHp());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_MANA, c.getMana());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_ARMOR, c.getArmor());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_MR, c.getMr());
        contentValues.put(ChampionContract.ChampionEntry.COLUMN_NAME_CHAMPION_DMG, c.getDmg());

        String selection= ChampionContract.ChampionEntry._ID+"= ?";
        String[] selectionArgs={ String.valueOf(c.getId())};

        int res= db.update(ChampionContract.ChampionEntry.TABLE_NAME,contentValues,selection,selectionArgs);

        db.close();
        return res;
    }

    public int deleteProduct(Champion c){

        SQLiteDatabase db= getWritableDatabase();

        String selection= ChampionContract.ChampionEntry._ID+"= ?";
        String[] selectionArgs={ String.valueOf(c.getId())};

        int res= db.delete(ChampionContract.ChampionEntry.TABLE_NAME,selection,selectionArgs);

        db.close();
        return res;
    }
}
