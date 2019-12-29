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
import com.example.android.gfhl.models.Item;
import com.example.android.gfhl.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

public class ItemViewModel extends AndroidViewModel {

    private static MutableLiveData<List<Item>> items;
    private Application application = getApplication();
    private final String ITEMS_URL_EN = "https://ddragon.leagueoflegends.com/cdn/9.24.2/data/en_US/item.json";
    private final String ITEMS_URL_ES = "https://ddragon.leagueoflegends.com/cdn/9.24.2/data/es_ES/item.json";

    public ItemViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Item>> getItems() {

        if (items ==null){
            items = new MutableLiveData<>();
            loadItems();
        }
        return items;
    }

    private void loadItems() {
        String languagename = Locale.getDefault().getDisplayLanguage();
        Uri baseUri = null;
        if(languagename.equals("English"))
            baseUri= Uri.parse(ITEMS_URL_EN);
        else
            baseUri= Uri.parse(ITEMS_URL_ES);
        Uri.Builder uriBuilder= baseUri.buildUpon();
        RequestQueue requestQueue= Volley.newRequestQueue(application);
        StringRequest request= new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String itemsJSON) {
                TreeSet<Item> itemsTS =  QueryUtils.extractItemsFromJson(itemsJSON);
                List<Item> itemsList = new ArrayList<Item>(itemsTS);
                items.setValue(itemsList);
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
