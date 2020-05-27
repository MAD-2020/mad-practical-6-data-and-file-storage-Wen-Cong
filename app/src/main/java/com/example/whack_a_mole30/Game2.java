package com.example.whack_a_mole30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Random;

public class Game2 extends AppCompatActivity {
    private final String TAG = "Whack-A-Mole 5-10";
    private Integer advancedScore;
    private TextView score;
    private int previousLocation;
    private int highScore;
    private int previousLocation2;
    private CountDownTimer countDownTimer;
    private CountDownTimer placeMoleTimer;
    private Button toHome;
    private int level;
    private int time;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        score = findViewById(R.id.scoreview2);
        toHome = findViewById(R.id.backHome2);
        level = getIntent().getIntExtra("level", 0);
        time = getIntent().getIntExtra("time", 0);
        user = (User) getIntent().getSerializableExtra("User");
        Log.v(TAG, "User: " + user.getUsername() + "\n Level: " + level + "\n Time: " + time);
        highScore = user.getScoreList()[level-1];
        advancedScore = 0;
        Log.v(TAG, "Current User Score: " + highScore);
        score.setText("" + advancedScore);
    }

    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v(TAG, "Button "+ button.getId() +" Clicked");
                    doCheck(button);
                    if(advancedScore >= highScore){
                        highScore = advancedScore;
                    }

                }
            });
        }

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyDBHandler dbHandler = new MyDBHandler(Game2.this, null, null, 1);
                Log.v(TAG, "Current return Score: " + highScore);
                //dbHandler.updateScore(user.getUsername(), level, highScore);
                int[] list = user.getScoreList();
                list[level-1] = highScore;
                user.setScoreList(list);
                Intent intent = new Intent(Game2.this, HomeActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        if (checkButton.getText() == "*") {
            advancedScore += 1;
            Log.v(TAG, "Scores a Point\n" + advancedScore);
            score.setText("" + advancedScore);
            setNewMole();
            setNew2ndMole();
        } else if (checkButton.getText() != "*") {
            advancedScore -= 1;
            Log.v(TAG, "Missed a Whack!\n" + advancedScore);
            score.setText("" + advancedScore);
            setNewMole();
            setNew2ndMole();
        }
    }

    public void setNewMole()
    {
        int randomLocation = 0;
        Random ran = new Random();
        randomLocation = ran.nextInt(8);
        int ranButton = (int) Array.get(BUTTON_IDS, randomLocation);
        Button b = findViewById(ranButton);
        b.setText("*");
        int previousButtonId = (int) Array.get(BUTTON_IDS, previousLocation);
        Button previousButton = findViewById(previousButtonId);
        previousButton.setText("o");
        previousLocation = randomLocation;
    }

    public void setNew2ndMole()
    {
        int randomLocation = 0;
        Random ran = new Random();
        randomLocation = ran.nextInt(8);
        int ranButton = (int) Array.get(BUTTON_IDS, randomLocation);
        Button b = findViewById(ranButton);
        b.setText("*");
        int previousButtonId = (int) Array.get(BUTTON_IDS, previousLocation2);
        Button previousButton = findViewById(previousButtonId);
        previousButton.setText("o");
        previousLocation2 = randomLocation;
    }

    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        placeMoleTimer = new CountDownTimer(500000, time) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "New Mole Location!");
                setNewMole();
                setNew2ndMole();
            }

            @Override
            public void onFinish() {
                placeMoleTimer.start();
            }
        };
        placeMoleTimer.start();
    }
    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
            R.id.b12, R.id.b22, R.id.b32, R.id.b42, R.id.b52, R.id.b62, R.id.b72, R.id.b82, R.id.b92
    };

    private void readyTimer(){

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "Ready Countdown!" + l/1000);
                Toast.makeText(Game2.this, "Get Ready in " + l/1000 + " Seconds", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Toast.makeText(Game2.this, "GO!", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
            }
        };
        countDownTimer.start();

    }
}
