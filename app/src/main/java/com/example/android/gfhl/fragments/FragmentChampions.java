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
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
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

import static androidx.recyclerview.widget.RecyclerView.LayoutManager;

public class FragmentChampions extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private RecyclerView recyclerView;
    private LayoutManager layoutManager;
    private ChampionAdapter adapter;
    private ChampionViewModel model =  null;
    private FavViewModel favModel = null;
    private ChampClicked callback;
    private Context context;
    private LinearLayout ly;

    private List<Champion> champList;
    static int posicion = 0;


    public FragmentChampions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState){

        View view = inflater.inflate(R.layout.champion_list, container, false);
        ly = view.findViewById(R.id.no_connection);

        context = this.getContext();

        recyclerView = view.findViewById(R.id.recyclerView);
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

            ly.setVisibility(View.GONE);

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
                                    posicion = position;
                                    callback.onChampionClicked(champ);
                                }
                            }, new ChampionAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Champion champ, int position) {
                                    posicion = position;
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

                            if(posicion != 0){ //Se utiliza para guardar la posicion del fragment al cambiar entre los fragment
                                recyclerView.getLayoutManager().scrollToPosition(posicion);
                                posicion = 0;
                            }

                            for (Champion fav : champsF) {
                                for (Champion c : champs) {
                                    if (fav.getName().equals(c.getName())) {
                                        favModel.loadSkins(c);
                                        c.setFav(true);
                                        c.setId(fav.getId());
                                    }
                                }
                            }

                            champList = champs;

                            setHasOptionsMenu(true);
                        }

                    });
                }
            });
        }  else
            ly.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)
        MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty() || newText.equals("")) {
            adapter = new ChampionAdapter(champList, R.layout.champion_row, getActivity(), new ChampionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Champion champ, int position) {
                    posicion = position;
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
            return false;
        }else {
            newText = newText.toLowerCase();
            final List<Champion> filteredNewsList = new ArrayList<>();
            for (Champion c : champList) {
                final String name = c.getName().toLowerCase();
                if (name.contains(newText)) {
                    filteredNewsList.add(c);
                }
            }
            adapter = new ChampionAdapter(filteredNewsList, R.layout.champion_row, getActivity(), new ChampionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Champion champ, int position) {
                    posicion = position;
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
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        onQueryTextSubmit("");
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        adapter = new ChampionAdapter(champList, R.layout.champion_row, getActivity(), new ChampionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Champion champ, int position) {
                posicion = position;
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
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        try {
            callback = (ChampClicked) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" deberia implementar la interfaz FavChampClicked");
        }
    }

    public interface ChampClicked {
        void onChampionClicked(Champion champion);
    }
}
