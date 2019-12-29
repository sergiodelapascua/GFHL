package com.example.android.gfhl.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gfhl.R;
import com.example.android.gfhl.models.Champion;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ViewHolder> {

    private List<Champion> champs;
    private int layout;
    private Activity activity;
    private OnItemClickListener listener;

    public ChampionAdapter(List<Champion> champs, int layout, Activity activity, OnItemClickListener listener) {
        this.champs = champs;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(activity).inflate(layout,viewGroup, false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        Champion champion= champs.get(i);
        Picasso.get().load(champion.getIcon()).into(viewHolder.imageViewIcon);
        viewHolder.textViewName.setText(champion.getName());
        viewHolder.textViewTitle.setText(champion.getTitle());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Champion champion= champs.get(i);
                listener.onItemClick(champion, i);
            }
        });

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Champion champion= champs.get(i);
                /*if (Objects.equals(view.getBackground().getConstantState(), activity.getResources().getDrawable(R.drawable.star).getConstantState()))
                    viewHolder.button.setBackgroundResource(R.drawable.star_gold);
                else
                    viewHolder.button.setBackgroundResource(R.drawable.star);*/
                if(champion.isFav()){
                    champion.setFav(false);
                    viewHolder.button.setBackgroundResource(R.drawable.star);
                } else {
                    champion.setFav(true);
                    viewHolder.button.setBackgroundResource(R.drawable.star_gold);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return champs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewIcon;
        TextView textViewName;
        TextView textViewTitle;
        ImageButton button;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.champIcon);
            textViewName = itemView.findViewById(R.id.champName);
            textViewTitle = itemView.findViewById(R.id.champTitle);
            button = itemView.findViewById(R.id.favIcon);
            linearLayout = itemView.findViewById(R.id.champion_row);
        }

    }

    public interface OnItemClickListener{
        void onItemClick(Champion champion, int position);
    }
}
