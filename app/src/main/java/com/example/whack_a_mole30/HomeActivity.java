package com.example.whack_a_mole30;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button signOut;
    private final String TAG = "HomeActivity";
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        signOut = findViewById(R.id.signOut);
        recyclerView = findViewById(R.id.recyclerView);
        user = (User) getIntent().getSerializableExtra("User");
        Log.v(TAG, user.getUsername() + " received");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LevelAdapter adapter = new LevelAdapter(user.getScoreList(), this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        for(int i = 0; i < user.getScoreList().length; i++){
            dbHandler.updateScore(user.getUsername(),i+1,user.getScoreList()[i]);
        }
    }
}
