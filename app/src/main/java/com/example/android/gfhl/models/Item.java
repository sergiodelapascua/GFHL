package com.example.android.gfhl.models;

import java.io.Serializable;

public class Item implements Serializable {

    private final String url = "http://ddragon.leagueoflegends.com/cdn/9.24.2/img/item/";//1001.png";

    private String imageUrl;
    private String name;
    private String descr;
    private String price;

    public Item(String id, String name, String descr, String price) {
        this.imageUrl = url+id+".png";
        this.name = name;
        this.descr = descr;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
    }

    public String getPrice() {
        return price;
    }
}
