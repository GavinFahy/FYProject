package com.example.fyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import com.example.fyproject.DataAccess.HP_DataAccess;
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
        //retrieves the users UUID
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //initials the text fields with Unique IDS
        final EditText EnterBreathing = findViewById(R.id.breathingText);
        final EditText EnterSight = findViewById(R.id.sightText);
        final EditText EnterHearing = findViewById(R.id.hearingText);
        final EditText EnterHeart = findViewById(R.id.heartText);
        final EditText EnterDisability = findViewById(R.id.disabilityText);
        final EditText EnterWheelchair = findViewById(R.id.wheelchairText);
        final EditText EnterOther = findViewById(R.id.otherText);

        //initials the check box's with Unique IDS
        final CheckBox BreathingCheck = findViewById(R.id.BreathingBox);
        final CheckBox SightCheck = findViewById(R.id.SightBox);
        final CheckBox HearingCheck = findViewById(R.id.HearingBox);
        final CheckBox HeartCheck = findViewById(R.id.HeartBox);
        final CheckBox DisabilityCheck = findViewById(R.id.DisabilityBox);
        final CheckBox WheelchairCheck = findViewById(R.id.WheelchairBox);
        final CheckBox OtherCheck = findViewById(R.id.OtherBox);


        Button Update = findViewById(R.id.Update);

        Update.setOnClickListener(V -> {
            //listens for the update button to be selected
            HashMap<String, Object> data = new HashMap<>();
            //data for the text fields
            data.put("breathingText", EnterBreathing.getText().toString());
            data.put("sightText", EnterSight.getText().toString());
            data.put("hearingText", EnterHearing.getText().toString());
            data.put("heartText", EnterHeart.getText().toString());
            data.put("disabilityText", EnterDisability.getText().toString());
            data.put("wheelchairText", EnterWheelchair.getText().toString());
            data.put("otherText", EnterOther.getText().toString());
            //data for the check box's
            data.put("BreathingCheck", BreathingCheck.isChecked());
            data.put("SightCheck", SightCheck.isChecked());
            data.put("HearingCheck", HearingCheck.isChecked());
            data.put("HeartCheck", HeartCheck.isChecked());
            data.put("DisabilityCheck", DisabilityCheck.isChecked());
            data.put("WheelchairCheck", WheelchairCheck.isChecked());
            data.put("OtherCheck", OtherCheck.isChecked());
            //Below the code updates the fields inside firebase
            HPDataAccess.update(currentUserId, data)
                    .addOnSuccessListener(suc -> {
                        //if successful it notifies the user.
                        Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(error -> {
                        Toast.makeText(this, "Failed to update" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        //this code is used to reference the firebase database for the HelathProblems table on the users UUID
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
                    sightCheck = Boolean.TRUE.equals(snapshot.child("SightCheck").getValue(Boolean.class));
                    hearingCheck = Boolean.TRUE.equals(snapshot.child("HearingCheck").getValue(Boolean.class));
                    heartCheck = Boolean.TRUE.equals(snapshot.child("HeartCheck").getValue(Boolean.class));
                    disabilityCheck = Boolean.TRUE.equals(snapshot.child("DisabilityCheck").getValue(Boolean.class));
                    wheelchairCheck = Boolean.TRUE.equals(snapshot.child("WheelchairCheck").getValue(Boolean.class));
                    otherCheck = Boolean.TRUE.equals(snapshot.child("OtherCheck").getValue(Boolean.class));
                    //the text fields are set corresponding to the values found in the firebase
                    EnterBreathing.setText(breathingText);
                    EnterSight.setText(sightText);
                    EnterHearing.setText(hearingText);
                    EnterHeart.setText(heartText);
                    EnterDisability.setText(disabilityText);
                    EnterWheelchair.setText(wheelchairText);
                    EnterOther.setText(otherText);
                    //the check boxes are set corresponding to the values that are found in the firebase
                    BreathingCheck.setChecked(breathingCheck);
                    SightCheck.setChecked(sightCheck);
                    HearingCheck.setChecked(hearingCheck);
                    HeartCheck.setChecked(heartCheck);
                    DisabilityCheck.setChecked(disabilityCheck);
                    WheelchairCheck.setChecked(wheelchairCheck);
                    OtherCheck.setChecked(otherCheck);
                }
            }

            //notify the user ifd something went wrong
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HealthProblems.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(HealthProblems.this, PersonalDetails.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }
}