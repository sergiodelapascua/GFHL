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
import com.example.android.gfhl.adapters.ChampionAdapter;
import com.example.android.gfhl.models.Champion;
import com.example.android.gfhl.utils.QueryUtils;
import com.example.android.gfhl.viewmodels.ChampionViewModel;

import java.util.List;

public class FragmentUno extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ChampionAdapter adapter;
    QueryUtils u = new QueryUtils();
    ChampionViewModel model =  null;
    List<Champion> champions;
    ChampClicked callback;
    Context context;

    public FragmentUno() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        context = this.getContext();

        recyclerView =(RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected= info != null && info.isConnected();

        if (isConnected){

            model = ViewModelProviders.of(this).get(ChampionViewModel.class);
            adapter = null;

            model.getChampions().observe(this, new Observer<List<Champion>>() {
                @Override
                public void onChanged(List<Champion> champs) {
                    adapter = new ChampionAdapter(champs, R.layout.item_row, getActivity(), new ChampionAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Champion champ, int position) {
                            callback.onChampionClicked(champ);
                            adapter.notifyItemChanged(position);
                        }
                    });
                    //emptyView.setText(R.string.no_earthquakes);
                }
            });


        } /*else{
            ProgressBar progressBar= findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet_connection);
        }*/

        recyclerView.setAdapter(adapter);
        return view;
    }

    public interface ChampClicked {
        void onChampionClicked(Champion champion);
    }
}
