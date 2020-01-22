package com.example.android.gfhl.viewmodels;

import android.app.Application;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.gfhl.data.DataBaseRoom;
import com.example.android.gfhl.models.Champion;
import com.example.android.gfhl.utils.QueryUtils;

import java.util.List;
import java.util.Locale;

public class FavViewModel extends AndroidViewModel {

    private static LiveData<List<Champion>> favChampions;
    private static DataBaseRoom db;
    private Application application = getApplication();
    private final String CHAMPION_DETAIL_EN = "https://ddragon.leagueoflegends.com/cdn/10.1.1/data/en_US/champion/";
    private final String CHAMPION_DETAIL_ES = "https://ddragon.leagueoflegends.com/cdn/10.1.1/data/es_ES/champion/";
    private List<String> skins;

    public FavViewModel(@NonNull Application application) {
        super(application);
        db = DataBaseRoom.getInstance(application);
        favChampions = db.getFavChampionDAO().getChampions();
    }

    public LiveData<List<Champion>> getChampions() {
        return favChampions;
    }

    public void searchSkins(List<Champion> championList) {
        for (Champion c : championList) {
            loadSkins(c);
        }
    }

    public void loadSkins(final Champion c) {
        final String name = c.getName();

        String languagename = Locale.getDefault().getDisplayLanguage();
        Uri baseUri = null;
        if (languagename.equals("English"))
            baseUri = Uri.parse(CHAMPION_DETAIL_EN + c.getName() + ".json");
        else
            baseUri = Uri.parse(CHAMPION_DETAIL_ES + c.getName() + ".json");

        Uri.Builder uriBuilder = baseUri.buildUpon();

        RequestQueue requestQueue = Volley.newRequestQueue(application);

        StringRequest request = new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String championDetailJSON) {
                skins = QueryUtils.extractLastSkinFromJson(championDetailJSON, name);
                c.setLore(skins.get(0));
                c.setSkinUrl(skins.subList(1,skins.size()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Volley", error.toString());
            }
        });

        requestQueue.add(request);
    }

    public void addChamp(Champion c) {
        new AsyncAddChampionDB().execute(c);
    }

    public void deleteChamp(Champion c) {
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
