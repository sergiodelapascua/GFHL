package com.example.android.gfhl.models;

public class Champion {

    private final String urlIcon = "http://ddragon.leagueoflegends.com/cdn/9.24.2/img/champion/";//Aatrox.png";

    private String icon;
    private String name;
    private String title;

    public Champion(String name, String title) {
        this.icon = urlIcon+name+".png";
        this.name = name;
        this.title = title;
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
}
