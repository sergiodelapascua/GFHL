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
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    public QueryUtils() {
    }

    /**
     * Return a list of {@link Champion} objects that has been built up from
     * parsing the given JSON response.
     */
    public static List<Champion> extractChampsFromJson(String championsJSON) {
        if (TextUtils.isEmpty(championsJSON)) {
            return null;
        }

        List<Champion> champions = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(championsJSON);

            JSONArray championArray = baseJsonResponse.getJSONArray("data");

            for (int i = 0; i < championArray.length(); i++) {

                JSONObject currentChampion = championArray.getJSONObject(i);

                String name = currentChampion.getString("id");

                String title = currentChampion.getString("title");

                Champion champ = new Champion(name, title);

                champions.add(champ);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the champion JSON results", e);
        }

        return champions;
    }






}
