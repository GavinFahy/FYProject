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


        Button Update = findViewById(R.id.Update);

        Update.setOnClickListener(V -> {
            String breathingText = Enterbreathing.getText().toString();
            String sightText = Entersight.getText().toString();
            String hearingText = Enterhearing.getText().toString();
            String heartText = Enterheart.getText().toString();
            String disabilityText = Enterdisability.getText().toString();
            String wheelchairText = Enterwheelchair.getText().toString();
            String otherText = Enterother.getText().toString();


            HashMap<String, Object> data = new HashMap<>();
            data.put("breathingText", Enterbreathing.getText().toString());
            data.put("sightText", Entersight.getText().toString());
            data.put("hearingText", Enterhearing.getText().toString());
            data.put("heartText", Enterheart.getText().toString());
            data.put("disabilityText", Enterdisability.getText().toString());
            data.put("wheelchairText", Enterwheelchair.getText().toString());
            data.put("otherText", Enterother.getText().toString());

            //data.put("BreathingCheck", BreathingCheck.isChecked());
            //data.put("BreathingBox", ((CheckBox) findViewById(R.id.BreathingBox)).isChecked());


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
                    String breathingText = snapshot.child("breathingText").getValue(String.class);
                    String sightText = snapshot.child("sightText").getValue(String.class);
                    String hearingText = snapshot.child("hearingText").getValue(String.class);
                    String heartText = snapshot.child("heartText").getValue(String.class);
                    String disabilityText = snapshot.child("disabilityText").getValue(String.class);
                    String wheelchairText = snapshot.child("wheelchairText").getValue(String.class);
                    String otherText = snapshot.child("otherText").getValue(String.class);



                    Enterbreathing.setText(breathingText);
                    Entersight.setText(sightText);
                    Enterhearing.setText(hearingText);
                    Enterheart.setText(heartText);
                    Enterdisability.setText(disabilityText);
                    Enterwheelchair.setText(wheelchairText);
                    Enterother.setText(otherText);


//
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HealthProblems.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });
    }
}