package com.example.android.gfhl.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "champions")
public class Champion implements Serializable, Comparable<Champion> {


    @PrimaryKey (autoGenerate = true)
    private long id;
    private String icon;
    private String image;
    private String name;
    private String title;
    private String hp;
    private String mana;
    private String armor;
    private String mr;
    private String dmg;
    private String skinName;
    private String skinUrl;
    private String lore;
    @Ignore
    private boolean fav;
    //private final String URL_ICON = "https://ddragon.leagueoflegends.com/cdn/9.24.2/img/champion/";//Aatrox.png";
    //private final String URL_IMAGE = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/";//Aatrox_0.jpg";

    @Ignore
    public Champion(String name, String title) {
        this.icon = "https://ddragon.leagueoflegends.com/cdn/9.24.2/img/champion/" +name+".png";
        this.name = name;
        this.title = title;
    }

    @Ignore
    public Champion(String name, String title, String hp, String mana, String armor, String mr, String dmg) {
        this.icon = "https://ddragon.leagueoflegends.com/cdn/9.24.2/img/champion/" +name+".png";
        this.image = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_0.jpg";
        this.name = name;
        this.title = title;
        this.hp = hp;
        this.mana = mana;
        this.armor = armor;
        this.mr = mr;
        this.dmg = dmg;
        fav = false;
    }

    public Champion(long id, String icon, String image, String name, String title, String hp, String mana, String armor, String mr, String dmg) {
        this.id = id;
        this.icon = icon;
        this.image = image;
        this.name = name;
        this.title = title;
        this.hp = hp;
        this.mana = mana;
        this.armor = armor;
        this.mr = mr;
        this.dmg = dmg;
    }

    public String getHp() {
        return hp;
    }

    public String getMana() {
        return mana;
    }

    public String getArmor() {
        return armor;
    }

    public String getMr() {
        return mr;
    }

    public String getDmg() {
        return dmg;
    }

    public String getIcon() { return icon; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() { return image; }

    public void setSkinName(String name) { this.skinName = name; }

    public String getSkinName() {  return skinName ; }

    public void setSkinUrl(String skinNum) { this.skinUrl = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum+".jpg"; }

    public String getSkinUrl() { return skinUrl; }

    public String getLore() { return lore; }

    public void setLore(String lore) { this.lore = lore; }

    public boolean isFav(){ return fav; }

    public void setFav(boolean fav) { this.fav = fav; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    @Override
    public int compareTo(Champion o) {
        if(this.name.equals(o.getName()) && this.icon.equals(o.getIcon()))
            return 0;
        return -1;
    }
}
