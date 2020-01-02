package com.example.android.gfhl.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.android.gfhl.R;
import com.example.android.gfhl.fragments.FragmentFavChampionsDetails;
import com.example.android.gfhl.models.Champion;

public class FavDetailsActivity extends AppCompatActivity implements FragmentFavChampionsDetails.FavChampClicked{

    FragmentFavChampionsDetails championDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_details);

        championDetails = new FragmentFavChampionsDetails();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_fav_detail, championDetails).commit();
    }

    @Override
    public void onFavChampionClicked() {
        championDetails.loadChampion((Champion) getIntent().getSerializableExtra("Champion"));
    }
}
