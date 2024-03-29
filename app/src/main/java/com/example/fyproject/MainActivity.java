package com.example.fyproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.Login);
        mAuth = FirebaseAuth.getInstance();
        //button that brings the user from the start page to the login page
        login.setOnClickListener(view -> {
            mAuth.signOut();
            //intent to send the user to the login page
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}