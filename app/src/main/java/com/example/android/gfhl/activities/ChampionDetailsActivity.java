package com.example.android.gfhl.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.android.gfhl.R;
import com.example.android.gfhl.fragments.FragmentFavChampionsDetails;
import com.example.android.gfhl.models.Champion;

public class ChampionDetailsActivity extends AppCompatActivity implements FragmentFavChampionsDetails.ChampClicked{
//public class ChampionDetailsActivity extends AppCompatActivity implements FragmentChampionDetails.ChampClicked{

    FragmentFavChampionsDetails championDetails;
    //FragmentChampionDetails championDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        championDetails = new FragmentFavChampionsDetails();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_detail, championDetails).commit();
    }

    @Override
    public void onChampionClicked() {
        championDetails.loadChampion((Champion) getIntent().getSerializableExtra("Champion"));
    }
}
