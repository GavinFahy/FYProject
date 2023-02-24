package com.example.fyproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyproject.DataAccess.PD_DataAccess;
import com.example.fyproject.Handlers.PDHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PersonalDetails extends AppCompatActivity {

    private PD_DataAccess pdDataAccess;
    private DatabaseReference reference;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details);

        pdDataAccess = new PD_DataAccess();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final EditText EnterName = findViewById(R.id.Name);
        final EditText EnterAge = findViewById(R.id.Age);
        final EditText EnterGender = findViewById(R.id.Gender);
        final EditText EnterWeight = findViewById(R.id.Weight);
        final EditText EnterContactNumber = findViewById(R.id.PhoneNumber);
        final EditText EnterConsultant = findViewById(R.id.Consultant);
        final EditText EnterNext_of_kin = findViewById(R.id.Next_of_kin);
        final EditText EnterNextOfKinNumber = findViewById(R.id.Number);
        Button Update = findViewById(R.id.update);

        Update.setOnClickListener(V -> {
            String name = EnterName.getText().toString();
            String age = EnterAge.getText().toString();
            String gender = EnterGender.getText().toString();
            String weight = EnterWeight.getText().toString();
            String phoneNumber = EnterContactNumber.getText().toString();
            String consultant = EnterConsultant.getText().toString();
            String nok = EnterNext_of_kin.getText().toString();
            String number = EnterNextOfKinNumber.getText().toString();

            //making sure none of the fields are empty
            if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || weight.isEmpty()
                    || phoneNumber.isEmpty() || consultant.isEmpty() || nok.isEmpty()
                    || number.isEmpty()) {
                Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
                return;
            }

            //check to see if the phone number is the correct amount of digits in length
            if (phoneNumber.length() != 10) {
                Toast.makeText(this, "Phone number must be 10 digits long", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("Name", EnterName.getText().toString());
            data.put("Age", EnterAge.getText().toString());
            data.put("Gender", EnterGender.getText().toString());
            data.put("Weight", EnterWeight.getText().toString());
            data.put("PhoneNumber", EnterContactNumber.getText().toString());
            data.put("Consultant", EnterConsultant.getText().toString());
            data.put("Next_of_kin", EnterNext_of_kin.getText().toString());
            data.put("Number", EnterNextOfKinNumber.getText().toString());

            pdDataAccess.update(currentUserId, data)
                    .addOnSuccessListener(suc -> {
                        Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(error -> {
                        Toast.makeText(this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        final TextView DisplayName = (TextView) findViewById(R.id.displayName);
        final TextView DisplayAge = (TextView) findViewById(R.id.displayAge);
        final TextView DisplayGender = (TextView) findViewById(R.id.displayGender);
        final TextView DisplayWeight = (TextView) findViewById(R.id.displayWeight);
        final TextView DisplayPhone = (TextView) findViewById(R.id.displayPhone);
        final TextView DisplayConsultant = (TextView) findViewById(R.id.displayConsultant);
        final TextView DisplayNext_of_kin = (TextView) findViewById(R.id.displayNext_of_kin);
        final TextView DisplayNextOfKinNumber = (TextView) findViewById(R.id.displayNumber);

        reference = FirebaseDatabase.getInstance().getReference("PDHandler").child(currentUserId).child("personalDetails");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PDHandler pdProfile = snapshot.getValue(PDHandler.class);

                if(pdProfile != null){
                   String name = pdProfile.Name;
                   String age = pdProfile.Age;
                   String gender = pdProfile.Gender;
                   String weight = pdProfile.Weight;
                   String phoneNumber = pdProfile.PhoneNumber;
                   String consultant = pdProfile.Consultant;
                   String next_of_kin = pdProfile.Next_of_kin;
                   String number = pdProfile.Number;
                   DisplayName.setText((name));
                   DisplayAge.setText(age);
                   DisplayGender.setText(gender);
                   DisplayWeight.setText(weight);
                   DisplayPhone.setText(phoneNumber);
                   DisplayConsultant.setText(consultant);
                   DisplayNext_of_kin.setText(next_of_kin);
                   DisplayNextOfKinNumber.setText(number);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PersonalDetails.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });

    }
}
