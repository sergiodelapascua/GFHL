package com.example.android.gfhl.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.gfhl.models.Champion;

import java.util.List;


@Dao
public interface FavChampionDAO {

    @Insert
    public long insertChampion(Champion product);

    @Update
    public int updateChampion(Champion product);

    @Delete
    public int deleteChampion(Champion product);

    @Query("SELECT * FROM CHAMPIONS")
    public LiveData<List<Champion>> getChampions();
}
