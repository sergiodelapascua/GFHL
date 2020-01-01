package com.example.android.gfhl.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gfhl.R;
import com.example.android.gfhl.adapters.FavAdapter;
import com.example.android.gfhl.models.Champion;
import com.example.android.gfhl.viewmodels.FavViewModel;

import java.util.List;

public class FragmentFavs extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FavAdapter adapter;
    FavViewModel model =  null;
    FragmentFavs.ChampClicked callback;
    Context context;

    public FragmentFavs() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState){
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

            model = ViewModelProviders.of(this).get(FavViewModel.class);
            adapter = null;

            model.getChampions().observe(this, new Observer<List<Champion>>() {


                @Override
                public void onChanged(List<Champion> champs) {
                    model.searchSkins(champs);
                    adapter = new FavAdapter(champs, R.layout.champion_fav_row, getActivity(), new FavAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Champion champ, int position) {
                            callback.onChampionClicked(champ);
                            adapter.notifyItemChanged(position);
                        }
                    });
                    recyclerView.setAdapter(adapter);

                }
            });


        }
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            callback = (FragmentFavs.ChampClicked) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" deberia implementar la interfaz ChampClicked");
        }
    }

    public interface ChampClicked {
        void onChampionClicked(Champion champion);
    }
}
