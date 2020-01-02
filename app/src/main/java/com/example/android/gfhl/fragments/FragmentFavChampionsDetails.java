package com.example.android.gfhl.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.gfhl.R;
import com.example.android.gfhl.models.Champion;
import com.squareup.picasso.Picasso;

public class FragmentFavChampionsDetails extends Fragment {

    TextView champNm;
    ImageView champImage;
    TextView hp;
    TextView mana;
    TextView armor;
    TextView mr;
    TextView dmg;
    ImageView lastSkin1;
    ImageView lastSkin2;
    ImageView lastSkin3;
    ImageView lastSkin4;
    ImageView lastSkin5;
    TextView lore;
    FavChampClicked callback;

    public FragmentFavChampionsDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState){

        View view = inflater.inflate(R.layout.champion_fav_details, container, false);

        champNm = view.findViewById(R.id.champNm);
        champImage = view.findViewById(R.id.champPhoto);
        hp = view.findViewById(R.id.hpNum);
        mana = view.findViewById(R.id.mpNum);
        armor = view.findViewById(R.id.armorNum);
        mr = view.findViewById(R.id.mrNum);
        dmg = view.findViewById(R.id.dmgNum);
        lastSkin1 = view.findViewById(R.id.skinPhoto1);
        lastSkin2 = view.findViewById(R.id.skinPhoto2);
        lastSkin3 = view.findViewById(R.id.skinPhoto3);
        lastSkin4 = view.findViewById(R.id.skinPhoto4);
        lastSkin5 = view.findViewById(R.id.skinPhoto5);
        lore = view.findViewById(R.id.lore);

        if(callback != null)
            callback.onFavChampionClicked();

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            callback = (FavChampClicked) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" deberia implementar el interfaz OnChampionSent");
        }
    }

    public void loadChampion(Champion champ) {
        champNm.setText(champ.getName());
        Picasso.get().load(champ.getImage()).into(champImage);
        hp.setText(champ.getHp());
        mana.setText(champ.getMana());
        armor.setText(champ.getArmor());
        mr.setText(champ.getMr());
        dmg.setText(champ.getDmg());
        Picasso.get().load(champ.getSkinUrl1()).into(lastSkin1);

        if (champ.getSkinUrl2() != null) {
            lastSkin2.setVisibility(View.VISIBLE);
            Picasso.get().load(champ.getSkinUrl2()).into(lastSkin2);
        } else
            lastSkin2.setVisibility(View.GONE);

        if (champ.getSkinUrl3() != null) {
            lastSkin3.setVisibility(View.VISIBLE);
            Picasso.get().load(champ.getSkinUrl3()).into(lastSkin3);
        } else
            lastSkin3.setVisibility(View.GONE);

        if (champ.getSkinUrl4() != null) {
            lastSkin4.setVisibility(View.VISIBLE);
            Picasso.get().load(champ.getSkinUrl4()).into(lastSkin4);
        } else
            lastSkin4.setVisibility(View.GONE);

        if (champ.getSkinUrl5() != null){
            lastSkin5.setVisibility(View.VISIBLE);
            Picasso.get().load(champ.getSkinUrl5()).into(lastSkin5);
        }else
            lastSkin5.setVisibility(View.GONE);


        lore.setText(champ.getLore());
    }

    public interface FavChampClicked {
        void onFavChampionClicked();
    }
}
