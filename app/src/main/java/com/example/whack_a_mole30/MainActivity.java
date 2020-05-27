package com.example.whack_a_mole30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username_login;
    EditText password_login;
    Button loginButton;
    TextView signUpButton;
    private  final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_login = findViewById(R.id.username_editText);
        password_login = findViewById(R.id.password_editText);
        loginButton = findViewById(R.id.Login);
        signUpButton = findViewById(R.id.signUp_page);
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username_login.getText().toString().trim();
                String pw = password_login.getText().toString().trim();
                User user = dbHandler.loadUser(name);
                if(user != null){
                    if(user.getPassword().equals(pw)){
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("User", user);
                        Log.v(TAG, user.getScoreList().length + " item in list loaded");
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
