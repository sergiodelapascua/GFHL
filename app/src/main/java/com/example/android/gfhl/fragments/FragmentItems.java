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
import com.example.android.gfhl.adapters.ItemAdapter;
import com.example.android.gfhl.models.Item;
import com.example.android.gfhl.utils.QueryUtils;
import com.example.android.gfhl.viewmodels.ItemViewModel;

import java.util.List;

public class FragmentItems extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ItemAdapter adapter;
    QueryUtils u = new QueryUtils();
    ItemViewModel model =  null;
    Context context;

    public FragmentItems() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {
        View view = inflater.inflate(R.layout.champion_list, container, false);

        context = this.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        if (isConnected) {

            model = ViewModelProviders.of(this).get(ItemViewModel.class);
            adapter = null;

            model.getItems().observe(this, new Observer<List<Item>>() {
                @Override
                public void onChanged(List<Item> items) {
                    adapter = new ItemAdapter(items, R.layout.item_row, getActivity());

                }
            });
        }

        return view;
    }
}
