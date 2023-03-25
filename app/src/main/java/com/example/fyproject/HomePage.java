package com.example.fyproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private Button PDButton;
    private Button VButton;
    private Button IButton;
    private Button Maps;
    private Button HP;
    private Button MH;
    private Button QR;
    private Button health;

    private Button alarm;

    FirebaseAuth Authorisation;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        //button that will redirect the user to the personal details page.
        PDButton = findViewById(R.id.PersonalDetails);
        PDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, PersonalDetails.class);
                startActivity(intent);
            }
        });

        //button that will redirect the user to the vaccinations page
        VButton = findViewById(R.id.Vac);
        VButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Vaccination.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the Infection's page
        IButton = findViewById(R.id.Infect);
        IButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Infections.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the Map page
        Maps = findViewById(R.id.Maps);
        Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the HealthProblems page
        HP = findViewById(R.id.HP);
        HP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, HealthProblems.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the MedicalHistory page
        MH = findViewById(R.id.MH);
        MH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, MedicalHistory.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the QRcode+scanner page
        QR = findViewById(R.id.QR);
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, QRCode.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the HealthCare page
        health = findViewById(R.id.health);
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, HealthCare.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the alarm page
        alarm = findViewById(R.id.alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Alarm.class);
                startActivity(intent);
            }
        });

        //button that will sign the user out and redirect them back the main activity.
        Button logout;
        logout = (Button)findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }
        });
   }
}
