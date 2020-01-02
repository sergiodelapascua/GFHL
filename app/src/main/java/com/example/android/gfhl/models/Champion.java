package com.example.android.gfhl.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

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
    private String skinUrl1;
    private String skinUrl2;
    private String skinUrl3;
    private String skinUrl4;
    private String skinUrl5;
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

    public void setSkinUrl(List<String> skinNum) {
        createUrl(skinNum);
    }

    public void createUrl(List<String>  skinNum){
        int numSkin = skinNum.size();
        if(numSkin >= 5){
            this.skinUrl1 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(0)+".jpg";
            this.skinUrl2 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(1)+".jpg";
            this.skinUrl3 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(2)+".jpg";
            this.skinUrl4 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(3)+".jpg";
            this.skinUrl5 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(4)+".jpg";
        } else if (numSkin == 4){
            this.skinUrl1 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(0)+".jpg";
            this.skinUrl2 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(1)+".jpg";
            this.skinUrl3 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(2)+".jpg";
            this.skinUrl4 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(3)+".jpg";
            this.skinUrl5 = null;
        } else if (numSkin == 3){
            this.skinUrl1 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(0)+".jpg";
            this.skinUrl2 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(1)+".jpg";
            this.skinUrl3 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(2)+".jpg";
            this.skinUrl4 = null;
            this.skinUrl5 = null;
        } else if (numSkin == 2){
            this.skinUrl1 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(0)+".jpg";
            this.skinUrl2 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(1)+".jpg";
            this.skinUrl3 = null;
            this.skinUrl4 = null;
            this.skinUrl5 = null;
        } else if (numSkin == 1){
            this.skinUrl1 = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+name+"_"+skinNum.get(0)+".jpg";
            this.skinUrl2 = null;
            this.skinUrl3 = null;
            this.skinUrl4 = null;
            this.skinUrl5 = null;
        }
    }

    public String getLore() { return lore; }

    public void setLore(String lore) { this.lore = lore; }

    public boolean isFav(){ return fav; }

    public void setFav(boolean fav) { this.fav = fav; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getSkinUrl1() { return skinUrl1; }

    public String getSkinUrl2() { return skinUrl2; }

    public String getSkinUrl3() { return skinUrl3; }

    public String getSkinUrl4() { return skinUrl4; }

    public String getSkinUrl5() { return skinUrl5; }

    public void setSkinUrl1(String skinUrl1) {
        this.skinUrl1 = skinUrl1;
    }

    public void setSkinUrl2(String skinUrl2) {
        this.skinUrl2 = skinUrl2;
    }

    public void setSkinUrl3(String skinUrl3) {
        this.skinUrl3 = skinUrl3;
    }

    public void setSkinUrl4(String skinUrl4) {
        this.skinUrl4 = skinUrl4;
    }

    public void setSkinUrl5(String skinUrl5) {
        this.skinUrl5 = skinUrl5;
    }

    @Override
    public int compareTo(Champion o) {
        if(this.name.equals(o.getName()) && this.icon.equals(o.getIcon()))
            return 0;
        return -1;
    }
}
