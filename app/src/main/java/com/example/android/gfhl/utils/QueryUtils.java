package com.example.android.gfhl.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.gfhl.models.Champion;
import com.example.android.gfhl.models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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

                String id = URLEncoder.encode(ids.getString(i),"UTF-8");

                JSONObject currentItem = data.getJSONObject(id);

                String name = currentItem.getString("name");
                byte ptextName[] = name.getBytes(Charset.forName("ISO-8859-1"));
                String valueName = new String(ptextName, Charset.forName("UTF-8"));
                //String desc = currentItem.getString("description");
                String desc = currentItem.getString("plaintext");
                byte ptextDesc[] = desc.getBytes(Charset.forName("ISO-8859-1"));
                String valueDesc = new String(ptextDesc, Charset.forName("UTF-8"));
                JSONObject gold = currentItem.getJSONObject("gold");
                String price = gold.getString("total");

                items.add(new Item(id, valueName, valueDesc, price));
            }

        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the items JSON results", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
                byte ptextName[] = name.getBytes(Charset.forName("ISO-8859-1"));
                String valueName = new String(ptextName, Charset.forName("UTF-8"));

                JSONObject currentChampion = data.getJSONObject(name);

                String title = currentChampion.getString("title");
                byte ptextTitle[] = title.getBytes(Charset.forName("ISO-8859-1"));
                String valueTitle = new String(ptextTitle, Charset.forName("UTF-8"));

                JSONObject stats = currentChampion.getJSONObject("stats");

                String hp = stats.getString("hp");
                String mana = stats.getString("mp");
                String armor = stats.getString("armor");
                String mr = stats.getString("spellblock");
                String dmg = stats.getString("attackdamage");

                //Champion champ = new Champion(name, title);
                Champion champ = new Champion(valueName, valueTitle, hp, mana, armor, mr, dmg);

                champions.add(champ);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the champion JSON results", e);
        }

        return champions;
    }

    public static List<String> extractLastSkinFromJson(String championDetailJSON, String champName) {
        List<String> champInfo = new ArrayList<>();

        if (TextUtils.isEmpty(championDetailJSON)) {
            return null;
        }

        try {

            JSONObject baseJsonResponse = new JSONObject(championDetailJSON);

            JSONObject data = baseJsonResponse.getJSONObject("data");
            JSONObject champ = data.getJSONObject(champName);

            String lore = champ.getString("lore");
            byte ptextLore[] = lore.getBytes(Charset.forName("ISO-8859-1"));
            String valueLore = new String(ptextLore, Charset.forName("UTF-8"));
            champInfo.add(valueLore);

            JSONArray skinsArray = champ.getJSONArray("skins");
            int longitud = skinsArray.length();
            int tope = Math.max(0, longitud-5);
            int cont = 0;
            for (int i=longitud-1; cont < 5; i-- ){
                JSONObject lastSkin = skinsArray.getJSONObject(i);
                if(Integer.parseInt(lastSkin.getString("num"))== 0)
                    break;
                champInfo.add(lastSkin.getString("num"));
                cont++;
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the champion JSON results", e);
        }

        return champInfo;
    }
}
