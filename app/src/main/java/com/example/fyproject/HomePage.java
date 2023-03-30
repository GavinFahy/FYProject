package com.example.fyproject;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyproject.DataAccess.PD_DataAccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {

    private Button Maps;
    private Button QR;
    private Button health;
    private Button forms;
    private Button alarm;

    private DatabaseReference reference;
    private String currentUserId;
    private PD_DataAccess pdDataAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        pdDataAccess = new PD_DataAccess();
        //retrieving the current users ID from firebase
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final EditText EnterName = findViewById(R.id.Name);

        reference = FirebaseDatabase.getInstance().getReference("PDHandler").child(currentUserId).child("personalDetails");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("Name").getValue(String.class);
                    EnterName.setText((name));
                }
            }

            //If something goes wrong the user is notified
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });

        //Button that will redirect the user to google maps
        Maps = findViewById(R.id.Maps);
        Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
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

        //Button that will redirect the user to the alarm page
        forms = findViewById(R.id.forms);
        forms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Forms.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem detailsButton = menu.findItem(R.id.action_personal_details);
        detailsButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(HomePage.this, PersonalDetails.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }
}
