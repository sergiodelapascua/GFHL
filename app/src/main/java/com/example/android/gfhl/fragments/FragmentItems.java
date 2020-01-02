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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gfhl.R;
import com.example.android.gfhl.adapters.ItemAdapter;
import com.example.android.gfhl.models.Item;
import com.example.android.gfhl.viewmodels.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentItems extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ItemAdapter adapter;
    private ItemViewModel model = null;
    private Context context;
    private LinearLayout ly;

    private List<Item> itemList;

    public FragmentItems() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {

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

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        if (isConnected) {
            ly.setVisibility(View.GONE);

            model = ViewModelProviders.of(this).get(ItemViewModel.class);

            model.getItems().observe(this, new Observer<List<Item>>() {
                @Override
                public void onChanged(List<Item> items) {
                    itemList = items;
                    adapter = new ItemAdapter(items, R.layout.item_row, getActivity());
                    recyclerView.setAdapter(adapter);
                    setHasOptionsMenu(true);
                }
            });
        } else
            ly.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        onQueryTextSubmit("");
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
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
            adapter = new ItemAdapter(itemList, R.layout.item_row, getActivity());
            recyclerView.setAdapter(adapter);
            return false;
        } else {
            newText = newText.toLowerCase();
            final List<Item> filteredNewsList = new ArrayList<>();
            for (Item i : itemList) {
                final String name = i.getName().toLowerCase();
                if (name.contains(newText) || i.getDescr().contains(newText)) {
                    filteredNewsList.add(i);
                }
            }
            adapter = new ItemAdapter(filteredNewsList, R.layout.item_row, getActivity());
            recyclerView.setAdapter(adapter);
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        adapter = new ItemAdapter(itemList, R.layout.item_row, getActivity());
        recyclerView.setAdapter(adapter);
        return true;
    }

}
