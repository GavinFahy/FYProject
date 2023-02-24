package com.example.fyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HealthProblems extends AppCompatActivity {

    private HP_DataAccess HPDataAccess;
    private DatabaseReference reference;
    private String currentUserId;
    boolean breathingCheck, sightCheck, hearingCheck, heartCheck, disabilityCheck, wheelchairCheck, otherCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_problems);

        HPDataAccess = new HP_DataAccess();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final EditText Enterbreathing = findViewById(R.id.breathingText);
        final EditText Entersight = findViewById(R.id.sightText);
        final EditText Enterhearing = findViewById(R.id.hearingText);
        final EditText Enterheart = findViewById(R.id.heartText);
        final EditText Enterdisability = findViewById(R.id.disabilityText);
        final EditText Enterwheelchair = findViewById(R.id.wheelchairText);
        final EditText Enterother = findViewById(R.id.otherText);

        final CheckBox BreathingCheck = findViewById(R.id.BreathingBox);
        final CheckBox SightCheck = findViewById(R.id.SightBox);
        final CheckBox HearingCheck = findViewById(R.id.HearingBox);
        final CheckBox HeartCheck = findViewById(R.id.HeartBox);
        final CheckBox DisabilityCheck = findViewById(R.id.DisabilityBox);
        final CheckBox WheelchairCheck = findViewById(R.id.WheelchairBox);
        final CheckBox OtherCheck = findViewById(R.id.OtherBox);


        Button Update = findViewById(R.id.Update);

        Update.setOnClickListener(V -> {

            HashMap<String, Object> data = new HashMap<>();
            //data for the text fields
            data.put("breathingText", Enterbreathing.getText().toString());
            data.put("sightText", Entersight.getText().toString());
            data.put("hearingText", Enterhearing.getText().toString());
            data.put("heartText", Enterheart.getText().toString());
            data.put("disabilityText", Enterdisability.getText().toString());
            data.put("wheelchairText", Enterwheelchair.getText().toString());
            data.put("otherText", Enterother.getText().toString());
            //data for the check box's
            data.put("BreathingCheck", BreathingCheck.isChecked());
            data.put("SightCheck", SightCheck.isChecked());
            data.put("HearingCheck", HearingCheck.isChecked());
            data.put("HeartCheck", HeartCheck.isChecked());
            data.put("DisabilityCheck", DisabilityCheck.isChecked());
            data.put("WheelchairCheck", WheelchairCheck.isChecked());
            data.put("OtherCheck", OtherCheck.isChecked());

            HPDataAccess.update(currentUserId, data)
                    .addOnSuccessListener(suc -> {
                        Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(error -> {
                        Toast.makeText(this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        reference = FirebaseDatabase.getInstance().getReference("HPHandler").child(currentUserId).child("HealthProblems");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //checking if data fields exist and if so display them
                    String breathingText = snapshot.child("breathingText").getValue(String.class);
                    String sightText = snapshot.child("sightText").getValue(String.class);
                    String hearingText = snapshot.child("hearingText").getValue(String.class);
                    String heartText = snapshot.child("heartText").getValue(String.class);
                    String disabilityText = snapshot.child("disabilityText").getValue(String.class);
                    String wheelchairText = snapshot.child("wheelchairText").getValue(String.class);
                    String otherText = snapshot.child("otherText").getValue(String.class);
                    //checking to see if check boxes have been check and if so they are displayed
                    breathingCheck = Boolean.TRUE.equals(snapshot.child("BreathingCheck").getValue(Boolean.class));
                    sightCheck = snapshot.child("SightCheck").getValue(Boolean.class);
                    hearingCheck = snapshot.child("HearingCheck").getValue(Boolean.class);
                    heartCheck = snapshot.child("HeartCheck").getValue(Boolean.class);
                    disabilityCheck = snapshot.child("DisabilityCheck").getValue(Boolean.class);
                    wheelchairCheck = snapshot.child("WheelchairCheck").getValue(Boolean.class);
                    otherCheck = snapshot.child("OtherCheck").getValue(Boolean.class);

                    Enterbreathing.setText(breathingText);
                    Entersight.setText(sightText);
                    Enterhearing.setText(hearingText);
                    Enterheart.setText(heartText);
                    Enterdisability.setText(disabilityText);
                    Enterwheelchair.setText(wheelchairText);
                    Enterother.setText(otherText);

                    BreathingCheck.setChecked(breathingCheck);
                    SightCheck.setChecked(sightCheck);
                    HearingCheck.setChecked(hearingCheck);
                    HeartCheck.setChecked(heartCheck);
                    DisabilityCheck.setChecked(disabilityCheck);
                    WheelchairCheck.setChecked(wheelchairCheck);
                    OtherCheck.setChecked(otherCheck);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HealthProblems.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });
    }
}