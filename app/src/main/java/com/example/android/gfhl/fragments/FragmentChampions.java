package com.example.android.gfhl.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gfhl.R;
import com.example.android.gfhl.adapters.ChampionAdapter;
import com.example.android.gfhl.models.Champion;
import com.example.android.gfhl.viewmodels.ChampionViewModel;
import com.example.android.gfhl.viewmodels.FavViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentChampions extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ChampionAdapter adapter;
    ChampionViewModel model =  null;
    FavViewModel favModel = null;
    ChampClicked callback;
    Context context;
    private List<Champion> newList;


    public FragmentChampions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState){

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.champion_list, container, false);

        context = this.getContext();

        recyclerView =(RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected= info != null && info.isConnected();

        if (isConnected){

            model = ViewModelProviders.of(this).get(ChampionViewModel.class);
            favModel = ViewModelProviders.of(this).get(FavViewModel.class);

            final LifecycleOwner owner = this.getViewLifecycleOwner();

            model.getChampions().observe(this, new Observer<List<Champion>>() {

                @Override
                public void onChanged(final List<Champion> champs) {

                    favModel.getChampions().observe(owner, new Observer<List<Champion>>() {

                        @Override
                        public void onChanged(List<Champion> champsF) {

                            adapter = new ChampionAdapter(champs, R.layout.champion_row, getActivity(), new ChampionAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Champion champ, int position) {
                                    callback.onChampionClicked(champ);
                                }
                            }, new ChampionAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Champion champ, int position) {
                                    if (champ.isFav()) {
                                        champ.setFav(false);
                                        favModel.deleteChamp(champ);
                                    } else {
                                        champ.setFav(true);
                                        favModel.addChamp(champ);
                                    }
                                }
                            });
                            recyclerView.setAdapter(adapter);

                            for (Champion fav : champsF){
                                for(Champion c : champs){
                                    if(fav.getName().equals(c.getName()))
                                        c.setFav(true);
                                        c.setId(fav.getId());
                                }
                            }

                            newList = champs;
                        }

                    });
                }
            });


        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        final MenuItem searchItem = menu.findItem(R.id.search);
        MenuItemCompat.setShowAsAction(searchItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Champion> aux = newList;
        if (newText == null || newText.trim().isEmpty() || newText.equals("")) {
            adapter.setFilter(aux);
            return false;
        }
        newText = newText.toLowerCase();
        final List<Champion> filteredNewsList = new ArrayList<>();
        for (Champion c : aux) {
            final String title = c.getName().toLowerCase();
            if (title.contains(newText)) {
                filteredNewsList.add(c);
            }
        }
        adapter.setFilter(filteredNewsList);
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        adapter.setFilter(newList);
        return true;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            callback = (ChampClicked) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" deberia implementar la interfaz ChampClicked");
        }
    }

    public interface ChampClicked {
        void onChampionClicked(Champion champion);
    }
}
