package com.example.fyproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private Button PDButton;
    private Button VButton;
    private Button IButton;
    private Button Maps;
    private Button HP;
    private Button MH;
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
//
//        //button that will redirect the user to the vaccinations page
//        VButton = findViewById(R.id.Vac);
//        VButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomePage.this, Vaccinations.class);
//                startActivity(intent);
//            }
//        });
//
        //Button that will redirect the user to the Infection's page
        IButton = findViewById(R.id.Infect);
        IButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Infections.class);
                startActivity(intent);
            }
        });

        Maps = findViewById(R.id.Maps);
        Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        HP = findViewById(R.id.HP);
        HP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, HealthProblems.class);
                startActivity(intent);
            }
        });

        MH = findViewById(R.id.MH);
        MH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, MedicalHistory.class);
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
