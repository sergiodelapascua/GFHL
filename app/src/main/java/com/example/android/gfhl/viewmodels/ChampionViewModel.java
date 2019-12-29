package com.example.android.gfhl.viewmodels;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

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
import com.example.android.gfhl.models.Champion;
import com.example.android.gfhl.utils.QueryUtils;

import java.util.List;
import java.util.Locale;

public class ChampionViewModel extends AndroidViewModel {

    private static MutableLiveData<List<Champion>> champions;
    private static List<Champion> championList;
    private static String[] skins;
    public static Champion auxChamp;
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
                championList= QueryUtils.extractChampsFromJson(championsJSON);
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
}
