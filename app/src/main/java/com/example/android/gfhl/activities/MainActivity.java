package com.example.android.gfhl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android.gfhl.R;
import com.example.android.gfhl.fragments.FragmentChampionDetails;
import com.example.android.gfhl.fragments.FragmentChampions;
import com.example.android.gfhl.fragments.FragmentFavs;
import com.example.android.gfhl.models.Champion;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FragmentChampions.ChampClicked, FragmentChampionDetails.ChampClicked, FragmentFavs.ChampClicked {

    boolean isMultiPanel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //multiPanel();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_champs, R.id.navigation_items, R.id.navigation_favs)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void multiPanel() {
        //isMultiPanel = (getSupportFragmentManager().findFragmentById(R.id.fragment2) != null);
    }

    @Override
    public void onChampionClicked(Champion champ) {
        //FragmentChampionDetails fragmentDetails = (FragmentChampionDetails) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        if (!isMultiPanel) {
            Intent intent = new Intent(MainActivity.this, ChampionDetailsActivity.class);
            intent.putExtra("Champion", champ);
            startActivity(intent);
        } else {
            //fragmentDetails.loadChampion(champ);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onChampionClicked() {

    }
}
