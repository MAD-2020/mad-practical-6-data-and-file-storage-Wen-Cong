package com.example.whack_a_mole30;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LevelAdapter extends RecyclerView.Adapter<LevelViewHolder> {
    int[] data;
    Activity activity;
    LevelAdapter(int[] scoreList, Activity homeActivity){
        data = scoreList;
        activity = homeActivity;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.levelview, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, final int position) {
        final int level = position + 1;
        holder.level.setText("Level " + level);
        holder.score.setText("" + data[position]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level <= 5){
                    Intent intent = new Intent(activity, Game1.class);
                    intent.putExtra("level", level);
                    intent.putExtra("time", 11000 - (level * 1000));
                    intent.putExtra("User", ((HomeActivity) activity).user);
                    activity.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(activity, Game2.class);
                    intent.putExtra("level", position + 1);
                    intent.putExtra("time", 11000 - (position * 1000));
                    intent.putExtra("User", ((HomeActivity) activity).user);
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
