package com.example.android.gfhl.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.gfhl.R;
import com.example.android.gfhl.fragments.FragmentChampionDetails;
import com.example.android.gfhl.fragments.FragmentChampions;
import com.example.android.gfhl.models.Champion;

public class MainActivity extends AppCompatActivity implements FragmentChampions.ChampClicked, FragmentChampionDetails.ChampClicked {

    boolean isMultiPanel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //multiPanel();
    }

    public void multiPanel() {
        isMultiPanel = (getSupportFragmentManager().findFragmentById(R.id.fragment2) != null);
    }

    @Override
    public void onChampionClicked(Champion champ) {
        FragmentChampionDetails fragmentDetails = (FragmentChampionDetails) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        if(!isMultiPanel){
            Intent intent = new Intent(MainActivity.this, ChampionDetailsActivity.class);
            intent.putExtra("Champion", champ);
            startActivity(intent);
        }else {
            fragmentDetails.loadChampion(champ);
        }
    }

    @Override
    public void onChampionClicked() {

    }
}
