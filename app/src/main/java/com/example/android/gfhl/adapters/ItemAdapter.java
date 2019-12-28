package com.example.android.gfhl.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gfhl.R;
import com.example.android.gfhl.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> items;
    private int layout;
    private Activity activity;
    private Context context;

    public ItemAdapter(List<Item> items, int layout, Activity activity) {
        this.items = items;
        this.layout = layout;
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(activity).inflate(layout,viewGroup, false);
        ItemAdapter.ViewHolder viewHolder= new ItemAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolder viewHolder, final int i) {

        final Item item= items.get(i);
        Picasso.get().load(item.getIcon()).into(viewHolder.imageViewIcon);
        viewHolder.textViewName.setText(item.getName());
        viewHolder.textViewDesc.setText(item.getDescr());
        viewHolder.textViewPrice.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewIcon;
        TextView textViewName;
        TextView textViewDesc;
        TextView textViewPrice;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.itemIcon);
            textViewName = itemView.findViewById(R.id.itemName);
            textViewDesc = itemView.findViewById(R.id.itemDesc);
            textViewPrice = itemView.findViewById(R.id.itemPrice);
            linearLayout = itemView.findViewById(R.id.item_row);
        }

    }
}
