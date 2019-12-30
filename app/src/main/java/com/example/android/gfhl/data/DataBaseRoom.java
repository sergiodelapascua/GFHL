package com.example.android.gfhl.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.gfhl.models.Champion;

@Database(entities = {Champion.class}, version = 2, exportSchema = false)
public abstract class DataBaseRoom extends RoomDatabase {

    public abstract FavChampionDAO favChampionDAO();
    private static DataBaseRoom INSTANCE=null;


    public static DataBaseRoom getInstance(final Context context){

        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), DataBaseRoom.class, "campeones favoritos.db").fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE=null;
    }
}
