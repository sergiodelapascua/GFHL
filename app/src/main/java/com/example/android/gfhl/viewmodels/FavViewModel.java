package com.example.android.gfhl.viewmodels;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.gfhl.data.DataBaseRoom;
import com.example.android.gfhl.models.Champion;

import java.util.List;

public class FavViewModel extends AndroidViewModel {

    private static LiveData<List<Champion>> favChampions;
    private static DataBaseRoom db;

    public FavViewModel(@NonNull Application application) {
        super(application);
        db=DataBaseRoom.getInstance(application);
        favChampions = db.getFavChampionDAO().getChampions();
    }

    public LiveData<List<Champion>> getChampions() {
        return favChampions;
    }

    public void addChamp(Champion c){
        new AsyncAddChampionDB().execute(c);
    }

    public void deleteChamp(Champion c){
        new AsynDeleteChampionDB().execute(c);
    }

    private class AsyncAddChampionDB extends AsyncTask<Champion, Void, Long> {

        Champion champ;

        @Override
        protected Long doInBackground(Champion... champions) {

            long id = -1;

            if (champions.length != 0) {
                String name = champions[0].getName();
                Log.d("Champion", name);
                champ = champions[0];
                id = db.getFavChampionDAO().insertChampion(champions[0]);
                champ.setId(id);
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id == -1) {
                Toast.makeText(getApplication(), "Error adding champ", Toast.LENGTH_SHORT)
                        .show();
            } else {

                Toast.makeText(getApplication(), "Champion added", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    private class AsynDeleteChampionDB extends AsyncTask<Champion, Void, Integer> {

        public AsynDeleteChampionDB() {

        }

        @Override
        protected Integer doInBackground(Champion... champions) {

            int deletedrows = 0;

            if (champions.length != 0) {

                deletedrows = db.getFavChampionDAO().deleteChampion(champions[0]);

            }

            return deletedrows;

        }

        @Override
        protected void onPostExecute(Integer deletedRows) {
            if (deletedRows == 0) {
                Toast.makeText(getApplication(), "Error deleting champ", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplication(), "Champion deleted", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }
}
