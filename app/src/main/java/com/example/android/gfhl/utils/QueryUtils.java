package com.example.android.gfhl.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.gfhl.models.Champion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static List<Champion> champions;

    public QueryUtils() {
    }

    public static List<Champion>  extractChampsFromJson(String championsJSON) {
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
}
