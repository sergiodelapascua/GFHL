package com.example.android.gfhl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android.gfhl.R;
import com.example.android.gfhl.fragments.FragmentChampionDetails;
import com.example.android.gfhl.fragments.FragmentChampions;
import com.example.android.gfhl.fragments.FragmentFavChampionsDetails;
import com.example.android.gfhl.fragments.FragmentFavs;
import com.example.android.gfhl.models.Champion;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FragmentChampions.ChampClicked, FragmentChampionDetails.ChampClicked, FragmentFavChampionsDetails.FavChampClicked, FragmentFavs.FavChampClicked {

    boolean isMultiPanel = false;
    private LinearLayout normalD;
    private LinearLayout favD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        normalD = findViewById(R.id.normal_details);
        favD = findViewById(R.id.fav_details);

        multiPanel();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_champs, R.id.navigation_items, R.id.navigation_favs)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void multiPanel() {
        isMultiPanel = (getSupportFragmentManager().findFragmentById(R.id.fragmentChampionDetails) != null);
    }

    @Override
    public void onFavChampionClicked(Champion champ) {
        FragmentFavChampionsDetails fragmentFavDetails = (FragmentFavChampionsDetails) getSupportFragmentManager().findFragmentById(R.id.fragmentFavChampionDetails);
        if (!isMultiPanel) {
            Intent intent = new Intent(MainActivity.this, FavDetailsActivity.class);
            intent.putExtra("Champion", champ);
            startActivity(intent);
        } else {
            favD.setVisibility(View.VISIBLE);
            normalD.setVisibility(View.GONE);
            fragmentFavDetails.loadChampion(champ);
        }
    }

    @Override
    public void onChampionClicked(Champion champ) {
        FragmentChampionDetails fragmentDetails = (FragmentChampionDetails) getSupportFragmentManager().findFragmentById(R.id.fragmentChampionDetails);
        if (!isMultiPanel) {
            Intent intent = new Intent(MainActivity.this, ChampionDetailsActivity.class);
            intent.putExtra("Champion", champ);
            startActivity(intent);
        } else {
            favD.setVisibility(View.GONE);
            normalD.setVisibility(View.VISIBLE);
            fragmentDetails.loadChampion(champ);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onChampionClicked() {

    }

    @Override
    public void onFavChampionClicked() {

    }
}