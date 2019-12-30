package com.example.android.gfhl.viewmodels;

import android.app.Application;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

public class ChampionViewModel extends AndroidViewModel {

    private static MutableLiveData<List<Champion>> champions;
    private static DataBaseRoom db;
    private Application application = getApplication();
    private final String CHAMPION_DETAIL_EN = "https://ddragon.leagueoflegends.com/cdn/9.24.2/data/en_US/champion/";
    private final String CHAMPION_DETAIL_ES = "https://ddragon.leagueoflegends.com/cdn/9.24.2/data/es_ES/champion/";
    private final String CHAMPIONS_URL_EN = "https://ddragon.leagueoflegends.com/cdn/9.24.2/data/en_US/champion.json";
    private final String CHAMPIONS_URL_ES = "https://ddragon.leagueoflegends.com/cdn/9.24.2/data/es_ES/champion.json";

    public ChampionViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Champion>> getChampions() {

        if (champions ==null){
            champions = new MutableLiveData<>();
            loadChamps();
        }
        return champions;
    }

    private void loadChamps() {
        /*SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(application);

        String orderBy = preferences.getString(
                application.getString(R.string.settings_order_by_key),
                application.getString(R.string.settings_order_by_default)
        );*/
        String languagename = Locale.getDefault().getDisplayLanguage();
        Uri baseUri = null;
        if(languagename.equals("English"))
            baseUri= Uri.parse(CHAMPIONS_URL_EN);
        else
            baseUri= Uri.parse(CHAMPIONS_URL_ES);
        Uri.Builder uriBuilder= baseUri.buildUpon();
        RequestQueue requestQueue= Volley.newRequestQueue(application);
        StringRequest request= new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String championsJSON) {
                List<Champion> championList= QueryUtils.extractChampsFromJson(championsJSON);
                champions.setValue(championList);
                //searchSkins();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Volley", error.toString());
            }
        });

        requestQueue.add(request);
    }

    private class AsyncAddProductDB extends AsyncTask<Champion, Void, Long> {

        Champion product;

        @Override
        protected Long doInBackground(Champion... champions) {

            long id = -1;

            if (champions.length != 0) {
                String name = champions[0].getName();
                Log.d("Product", name);
                product = champions[0];
                id = db.favChampionDAO().insertChampion(champions[0]);
                product.setId(id);
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

    private class AsyncEditProductDB extends AsyncTask<Champion, Void, Integer> {



        public AsyncEditProductDB() {

        }

        @Override
        protected Integer doInBackground(Champion... champions) {
            int updatedrows = 0;
            if (champions.length != 0) {

                updatedrows = db.favChampionDAO().updateChampion(champions[0]);

            }

            return updatedrows;
        }

        @Override
        protected void onPostExecute(Integer updatedRows) {
            if (updatedRows == 0) {
                Toast.makeText(getApplication(), "Error updating champ", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplication(), "Champion updated", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private class AsynDeleteProductDB extends AsyncTask<Champion, Void, Integer> {

        public AsynDeleteProductDB() {

        }

        @Override
        protected Integer doInBackground(Champion... champions) {

            int deletedrows = 0;

            if (champions.length != 0) {

                deletedrows = db.favChampionDAO().deleteChampion(champions[0]);

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

    /*private void searchSkins(){
        for (int i = 0; i < championList.size(); i++){
            Champion c = championList.get(i);
            final String name  = c.getName();
            String url = CHAMPION_DETAIL_EN + c.getName() + ".json";
            loadSkins(url, c.getName());
        }
    }

    private void loadSkins(String url, final String name) {

            Uri baseUri = Uri.parse(url);
            Uri.Builder uriBuilder = baseUri.buildUpon();

            RequestQueue requestQueue = Volley.newRequestQueue(application);

            StringRequest request = new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {

                @Override
                public void onResponse(String championDetailJSON) {
                    List<Champion> auxChampionList = new ArrayList<>();
                    auxChampionList = champions.getValue();
                    skins = QueryUtils.extractLastSkinFromJson(championDetailJSON, name);
                    auxChamp.setSkinName(skins[0]);
                    auxChamp.setSkinUrl(skins[1]);
                    auxChampionList.add(auxChamp);
                    champions.setValue(auxChampionList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error Volley", error.toString());
                }
            });
    }*/
}
