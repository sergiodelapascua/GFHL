package com.example.android.gfhl.models;

import java.io.Serializable;

public class Item implements Serializable, Comparable<Item> {

    private final String url = "https://ddragon.leagueoflegends.com/cdn/9.24.2/img/item/";//1001.png";

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

    public String getIcon() { return imageUrl; }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public int compareTo(Item o1) {
        if(Integer.parseInt(this.price)- Integer.parseInt(o1.getPrice()) == 0)
            return this.name.compareTo(o1.getName());
        else
            return Integer.parseInt(this.price)- Integer.parseInt(o1.getPrice());
    }
}
