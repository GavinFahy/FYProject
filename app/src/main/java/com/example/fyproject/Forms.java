package com.example.fyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Forms extends AppCompatActivity {

    private Button VButton;
    private Button IButton;
    private Button HP;
    private Button MH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);

        //button that will redirect the user to the vaccinations page
        VButton = findViewById(R.id.Vac);
        VButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forms.this, Vaccination.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the Infection's page
        IButton = findViewById(R.id.Infect);
        IButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forms.this, Infections.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the HealthProblems page
        HP = findViewById(R.id.HP);
        HP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forms.this, HealthProblems.class);
                startActivity(intent);
            }
        });

        //Button that will redirect the user to the MedicalHistory page
        MH = findViewById(R.id.MH);
        MH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forms.this, MedicalHistory.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem detailsButton = menu.findItem(R.id.action_personal_details);
        detailsButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Forms.this, PersonalDetails.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }
}