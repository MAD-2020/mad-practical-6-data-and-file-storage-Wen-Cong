package com.example.whack_a_mole30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button signUp;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.username_signUp);
        password = findViewById(R.id.password_signUp);
        signUp = findViewById(R.id.SignUp);
        cancel = findViewById(R.id.cancel_signUp);
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String pw = password.getText().toString().trim();
                User user = dbHandler.loadUser(name);
                if(user == null){
                    user = new User(name, pw);
                    dbHandler.addUser(user);
                    Toast.makeText(SignUpActivity.this, "Profile Created Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "User Existed, Sign In instead!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
