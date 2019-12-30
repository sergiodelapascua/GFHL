package com.example.android.gfhl.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.List;

public class FragmentChampions extends Fragment{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ChampionAdapter adapter;
    ChampionViewModel model =  null;
    FavViewModel favModel = null;
    ChampClicked callback;
    Context context;
    List<Champion> newList;


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
                        }

                    });
                }
            });


        }
        return view;
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
