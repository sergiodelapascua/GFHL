package com.example.android.gfhl.adapters;

import android.app.Activity;
import android.content.Context;
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
import java.util.Objects;

public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ViewHolder> {

    private List<Champion> champs;
    private int layout;
    private Activity activity;
    private OnItemClickListener listener;
    private Context context;

    public ChampionAdapter(List<Champion> champs, int layout, Activity activity, OnItemClickListener listener) {
        this.champs = champs;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
        this.context = activity.getApplicationContext();
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

        final Champion champion= champs.get(i);
        Picasso.get().load(champion.getIcon()).into(viewHolder.imageViewIcon);
        viewHolder.textViewName.setText(champion.getName());
        viewHolder.textViewTitle.setText(champion.getTitle());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(champion, i);
            }
        });

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(view.getBackground().getConstantState(), activity.getResources().getDrawable(R.drawable.star).getConstantState()))
                //if(viewHolder.button.getBackground().getConstantState()== ContextCompat.getDrawable(this.getActivity(), R.drawable.star))
                    viewHolder.button.setBackgroundResource(R.drawable.star_gold);
                else
                    viewHolder.button.setBackgroundResource(R.drawable.star);
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
