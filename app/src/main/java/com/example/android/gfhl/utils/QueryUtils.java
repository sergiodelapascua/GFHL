package com.example.android.gfhl.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.gfhl.models.Champion;
import com.example.android.gfhl.models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class QueryUtils {

    public QueryUtils() {
    }

    public static TreeSet<Item> extractItemsFromJson(String itemsJSON) {

        if (TextUtils.isEmpty(itemsJSON)) {
            return null;
        }

        TreeSet<Item> items = new TreeSet<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(itemsJSON);

            JSONObject data = baseJsonResponse.getJSONObject("data");
            JSONArray ids = data.names();

            for (int i=0; i < ids.length(); i++){

                String id = ids.getString(i);

                JSONObject currentItem = data.getJSONObject(id);

                String name = currentItem.getString("name");
                String desc = currentItem.getString("plaintext");
                JSONObject gold = currentItem.getJSONObject("gold");
                String  price = gold.getString("total");
            }

        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the items JSON results", e);
        }

        return items;
    }

    public static List<Champion> extractChampsFromJson(String championsJSON) {
        if (TextUtils.isEmpty(championsJSON)) {
            return null;
        }

        List<Champion> champions = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(championsJSON);

            JSONObject data = baseJsonResponse.getJSONObject("data");
            JSONArray championArray = data.names();

            for (int i = 0; i < championArray.length(); i++) {

                String name = championArray.getString(i);

                JSONObject currentChampion = data.getJSONObject(name);

                String title = currentChampion.getString("title");

                JSONObject stats = currentChampion.getJSONObject("stats");

                String hp = stats.getString("hp");
                String mana = stats.getString("mp");
                String armor = stats.getString("armor");
                String mr = stats.getString("spellblock");
                String dmg = stats.getString("attackdamage");

                //Champion champ = new Champion(name, title);
                Champion champ = new Champion(name, title, hp, mana, armor, mr, dmg);

                champions.add(champ);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the champion JSON results", e);
        }

        return champions;
    }

    public static String[] extractLastSkinFromJson(String championDetailJSON, String champName) {
        String[] skinInfo = new String[2];

        if (TextUtils.isEmpty(championDetailJSON)) {
            return null;
        }

        try {

            JSONObject baseJsonResponse = new JSONObject(championDetailJSON);

            JSONObject data = baseJsonResponse.getJSONObject("data");
            JSONObject champ = data.getJSONObject(champName);
            JSONArray skinsArray = champ.getJSONArray("skins");

            JSONObject lastSkin = skinsArray.getJSONObject(skinsArray.length() - 1);

            skinInfo[0] = lastSkin.getString("name");
            skinInfo[1] = lastSkin.getString("num");

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the champion JSON results", e);
        }

        return skinInfo;
    }
}
