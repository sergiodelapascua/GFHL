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

public class FragmentChampionDetails extends Fragment {

    TextView champNm;
    ImageView champImage;
    TextView hp;
    TextView mana;
    TextView armor;
    TextView mr;
    TextView dmg;
    TextView skinName;
    ImageView lastSkin;
    ChampClicked callback;

    public FragmentChampionDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState){

        View view = inflater.inflate(R.layout.champion_details, container, false);

        champNm = view.findViewById(R.id.champNm);
        champImage = view.findViewById(R.id.champPhoto);
        hp = view.findViewById(R.id.hpNum);
        mana = view.findViewById(R.id.mpNum);
        armor = view.findViewById(R.id.armorNum);
        mr = view.findViewById(R.id.mrNum);
        dmg = view.findViewById(R.id.dmgNum);
        skinName = view.findViewById(R.id.skinName);
        lastSkin = view.findViewById(R.id.skinPhoto);

        if(callback != null)
            callback.onChampionClicked();

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            callback = (FragmentChampionDetails.ChampClicked) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" deberia implementar el interfaz OnChampionSent");
        }
    }

    public void loadChampion(Champion champ){
        champNm.setText(champ.getName());
        Picasso.get().load(champ.getImage()).into(champImage);
        hp.setText(champ.getHp());
        mana.setText(champ.getMana());
        armor.setText(champ.getArmor());
        mr.setText(champ.getMr());
        dmg.setText(champ.getDmg());
        System.out.println("EEEO"+champ.getSkinName());
        skinName.setText(champ.getSkinName());
        Picasso.get().load(champ.getSkinUrl()).into(lastSkin);
        System.out.println("DESPUESSSSSSSSSSSSSSSSSSSSSSSS");
    }

    public interface ChampClicked {
        void onChampionClicked();
    }
}
