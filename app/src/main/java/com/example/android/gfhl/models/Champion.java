package com.example.android.gfhl.models;

import java.io.Serializable;

public class Champion implements Serializable {

    private final String URL_ICON = "https://ddragon.leagueoflegends.com/cdn/9.24.2/img/champion/";//Aatrox.png";
    private final String URL_IMAGE = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/";//Aatrox_0.jpg";

    private String icon;
    private String image;
    private String name;
    private String title;
    private String hp;
    private String mana;
    private String armor;
    private String mr;
    private String dmg;

    public Champion(String name, String title) {
        this.icon = URL_ICON +name+".png";
        this.name = name;
        this.title = title;
    }

    public Champion(String name, String title, String hp, String mana, String armor, String mr, String dmg) {
        this.icon = URL_ICON +name+".png";
        this.image = URL_IMAGE+name+"_0.jpg";
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

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getMana() {
        return mana;
    }

    public void setMana(String mana) {
        this.mana = mana;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getDmg() {
        return dmg;
    }

    public void setDmg(String dmg) {
        this.dmg = dmg;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}
