package com.alex.talk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alex.talk.AddOrViewRecordActivity;
import com.alex.talk.R;
import com.alex.talk.model.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder>{
    public static List<Player> players;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameV;
        public ImageView imageV, removeV;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            nameV = v.findViewById(R.id.nameV);
            imageV = v.findViewById(R.id.imageV);
        }
    }

    /**
     * Konstruktor
     * @param myDataset
     * @param context
     */
    public PlayerAdapter(List<Player> myDataset, Context context) {
        players = myDataset;
        this.context = context;
    }

    // Create new views
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Player player = players.get(position);
        holder.nameV.setText(player.getName());
        byte gender = player.getGender();
        int color = player.getColor();

        if(gender == 0){
            switch (color){
                case 1:
                    holder.imageV.setImageResource(R.drawable.man_blood);
                    break;
                case 2:
                    holder.imageV.setImageResource(R.drawable.man_salmon);
                    break;
                case 3:
                    holder.imageV.setImageResource(R.drawable.man_orange);
                    break;
                case 4:
                    holder.imageV.setImageResource(R.drawable.man_gold);
                    break;
                case 5:
                    holder.imageV.setImageResource(R.drawable.man_grass);
                    break;
                case 6:
                    holder.imageV.setImageResource(R.drawable.man_lime);
                    break;
                case 7:
                    holder.imageV.setImageResource(R.drawable.man_turquoise);
                    break;
                case 8:
                    holder.imageV.setImageResource(R.drawable.man_marine);
                    break;
                case 9:
                    holder.imageV.setImageResource(R.drawable.man_purple);
                    break;
                case 10:
                    holder.imageV.setImageResource(R.drawable.man_pink);
                    break;
            }
        } else if(gender == 1){
            switch (color){
                case 1:
                    holder.imageV.setImageResource(R.drawable.woman_blood);
                    break;
                case 2:
                    holder.imageV.setImageResource(R.drawable.woman_salmon);
                    break;
                case 3:
                    holder.imageV.setImageResource(R.drawable.woman_orange);
                    break;
                case 4:
                    holder.imageV.setImageResource(R.drawable.woman_gold);
                    break;
                case 5:
                    holder.imageV.setImageResource(R.drawable.woman_grass);
                    break;
                case 6:
                    holder.imageV.setImageResource(R.drawable.woman_lime);
                    break;
                case 7:
                    holder.imageV.setImageResource(R.drawable.woman_turquois);
                    break;
                case 8:
                    holder.imageV.setImageResource(R.drawable.woman_marine);
                    break;
                case 9:
                    holder.imageV.setImageResource(R.drawable.woman_purple);
                    break;
                case 10:
                    holder.imageV.setImageResource(R.drawable.woman_pink);
                    break;
            }
        } else {
            switch (color){
                case 1:
                    holder.imageV.setImageResource(R.drawable.nb_blood);
                    break;
                case 2:
                    holder.imageV.setImageResource(R.drawable.nb_salmon);
                    break;
                case 3:
                    holder.imageV.setImageResource(R.drawable.nb_orange);
                    break;
                case 4:
                    holder.imageV.setImageResource(R.drawable.nb_gold);
                    break;
                case 5:
                    holder.imageV.setImageResource(R.drawable.nb_grass);
                    break;
                case 6:
                    holder.imageV.setImageResource(R.drawable.nb_lime);
                    break;
                case 7:
                    holder.imageV.setImageResource(R.drawable.nb_turquoise);
                    break;
                case 8:
                    holder.imageV.setImageResource(R.drawable.nb_marine);
                    break;
                case 9:
                    holder.imageV.setImageResource(R.drawable.nb_purple);
                    break;
                case 10:
                    holder.imageV.setImageResource(R.drawable.nb_pink);
                    break;
            }
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToView = new Intent(context, AddOrViewRecordActivity.class);
                goToView.putExtra("USER_ID", player.getId());
                goToView.putExtra("addRecord", false);
                context.startActivity(goToView);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (players != null)
            return players.size();
        return 0;
    }
}
