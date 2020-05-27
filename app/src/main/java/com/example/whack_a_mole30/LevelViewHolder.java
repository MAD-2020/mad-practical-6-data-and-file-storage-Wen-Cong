package com.example.whack_a_mole30;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LevelViewHolder extends RecyclerView.ViewHolder {
    TextView score;
    TextView level;
    View view;
    public LevelViewHolder(@NonNull View itemView) {
        super(itemView);
        score = itemView.findViewById(R.id.scoreView);
        level = itemView.findViewById(R.id.levelName);
        view = itemView.findViewById(R.id.levelSelect);

    }
}
